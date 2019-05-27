package io.banditoz.twilcord;

import com.qmetric.spark.authentication.AuthenticationDetails;
import com.qmetric.spark.authentication.BasicAuthenticationFilter;
import com.twilio.twiml.MessagingResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.HttpClients;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;
import spark.Spark;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TwilioMessageEvent extends Thread {
    public void run() {
        Spark.before(
                new BasicAuthenticationFilter("/sms/*", new AuthenticationDetails(
                        new SettingsManager().getSettings().getWebUsername(),
                        new SettingsManager().getSettings().getWebPassword()
                )
        ));
        Spark.get("/sms/", (req, res) -> "Hello World");
        Spark.post("/sms/", (req, res) -> {
            try {
                res.type("application/xml");
                Map<String, String> parameters = parseBody(req.body());
                int numMedia = Integer.parseInt(parameters.get("NumMedia"));
                StringBuilder message = new StringBuilder(parameters.get("Body"));

                if (numMedia > 0) {
                    while (numMedia > 0) {
                        numMedia = numMedia - 1;

                        // Get all info
                        String mediaUrl = parameters.get(String.format("MediaUrl%d", numMedia));
                        String contentType = parameters.get(String.format("MediaContentType%d", numMedia));
                        String fileName = mediaUrl.substring(mediaUrl.lastIndexOf("/") + 1);
                        MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
                        MimeType mimeType = allTypes.forName(contentType);
                        String fileExtension = mimeType.getExtension();
                        File file = new File(fileName + fileExtension);

                        // Download the redirect URL
                        URL url = new URL(mediaUrl);
                        HttpGet get = new HttpGet(url.toURI());
                        HttpClient hc = HttpClients.createDefault();
                        HttpClientContext con = HttpClientContext.create();
                        hc.execute(get, con);
                        List<URI> redirectURIs = con.getRedirectLocations();
                        URI finalURI = new URI("");
                        if (redirectURIs != null && !redirectURIs.isEmpty()) {
                            finalURI = redirectURIs.get(redirectURIs.size() - 1);
                        }

                        message.insert(0, "<MMS IMAGE> " + finalURI.toString() + "\n");
                    }
                }
                if (message.toString().length() >= 2000) {
                    TwilioBot.sendSystemMessageToSMS("Your message was greater than 2000 characters! (contains " + message.toString().length() + " characters. (Most likely due to a really long message, with images included, or both.)) Therefore, it was not delivered over the bridge.");
                }
                else {
                    sendToChannel(message.toString());
                }
                MessagingResponse twiml = new MessagingResponse.Builder().build();
                return twiml.toXml();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        });
    }

    public static Map<String, String> parseBody(String body) throws UnsupportedEncodingException {
        String[] unparsedParams = body.split("&");
        Map<String, String> parsedParams = new HashMap<String, String>();
        for (int i = 0; i < unparsedParams.length; i++) {
            String[] param = unparsedParams[i].split("=");
            if (param.length == 2) {
                parsedParams.put(urlDecode(param[0]), urlDecode(param[1]));
            } else if (param.length == 1) {
                parsedParams.put(urlDecode(param[0]), "");
            }
        }
        return parsedParams;
    }

    public static String urlDecode(String s) throws UnsupportedEncodingException {
        return URLDecoder.decode(s, "utf8");
    }

    private void sendToChannel(String message) {
        TwilioBot.sendMessageToDiscord(message);
    }
}

