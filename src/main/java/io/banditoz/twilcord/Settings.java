package io.banditoz.twilcord;

public class Settings {
    private String discordToken;
    private String sid;
    private String twilloToken;
    private String sendNumber;
    private String recvNumber;
    private String channel;
    private String guild;
    private String webUsername;
    private String webPassword;

    public String getDiscordToken() {
        return discordToken;
    }

    public void setDiscordToken(String discordToken) {
        this.discordToken = discordToken;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getTwilloToken() {
        return twilloToken;
    }

    public void setTwilloToken(String twilloToken) {
        this.twilloToken = twilloToken;
    }

    public String getSendNumber() {
        return sendNumber;
    }

    public void setSendNumber(String sendNumber) {
        this.sendNumber = sendNumber;
    }

    public String getRecvNumber() {
        return recvNumber;
    }

    public void setRecvNumber(String recvNumber) {
        this.recvNumber = recvNumber;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getGuild() {
        return guild;
    }

    public void setGuild(String guild) {
        this.guild = guild;
    }

    public String getWebUsername() {
        return webUsername;
    }

    public void setWebUsername(String webUsername) {
        this.webUsername = webUsername;
    }

    public String getWebPassword() {
        return webPassword;
    }

    public void setWebPassword(String webPassword) {
        this.webPassword = webPassword;
    }
}
