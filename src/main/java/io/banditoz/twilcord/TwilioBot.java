package io.banditoz.twilcord;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class TwilioBot {
    private static PhoneNumber recvNumber;
    private static PhoneNumber sendNumber;

    public TwilioBot(String sid, String token, String recvNumber, String sendNumber) {
        Twilio.init(sid, token);
        TwilioBot.recvNumber = new PhoneNumber(recvNumber);
        TwilioBot.sendNumber = new PhoneNumber(sendNumber);
    }

    public static void sendMessageToSMS(MessageReceivedEvent e, String message) {
        String toSend = "<" + e.getAuthor().getName() + "> " + message;
        System.out.println("[M->SMS] " + toSend);
        Message.creator(recvNumber, sendNumber, toSend).create();
    }

    public static void sendSystemMessageToSMS(String message) {
        String toSend = "<SYSTEM MESSAGE> " + message;
        System.out.println("[S->SMS] " + toSend);
        Message.creator(recvNumber, sendNumber, toSend).create();
    }

    public static void sendMessageToMMS(MessageReceivedEvent e, String message, List<URI> image) {
        String toSend = "<" + e.getAuthor().getName() + "> " + message;
        StringBuilder mediaURIs = new StringBuilder();
        for (URI u : image) {
            mediaURIs.append(u.toString());
            mediaURIs.append(" ");
        }
        System.out.println("[M->MMS] " + toSend + "\n" + "Images: " + mediaURIs.toString());
        Message.creator(recvNumber, sendNumber, message).setMediaUrl(image).create();
    }

    public static void sendMessageToDiscord(String msg) {
        System.out.println("[M->DSC] " + msg);
        Twilcord.jda.getTextChannelById(SettingsManager.getInstance().getSettings().getChannel()).sendMessage(msg).queue();
    }

    public static void sendSystemMessageToDiscord(String msg) {
        System.out.println("[S->DSC] " + msg);
        Twilcord.jda.getTextChannelById(SettingsManager.getInstance().getSettings().getChannel()).sendMessage("**<SYSTEM MESSAGE>** " + msg).queue();
    }
}
