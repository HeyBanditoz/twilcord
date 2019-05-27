package io.banditoz.twilcord;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Guild;

import javax.security.auth.login.LoginException;

public class Twilcord {
    public static JDA jda;
    private static TwilioBot tb;

    public static void setupBot() throws LoginException, InterruptedException {
        Settings settings = SettingsManager.getInstance().getSettings();
        jda = new JDABuilder(settings.getDiscordToken()).build();
        jda.awaitReady();
        tb = new TwilioBot(settings.getSid(), settings.getTwilloToken(), settings.getRecvNumber(), settings.getSendNumber());
        jda.addEventListener(new DiscordMessageEvent());
        jda.addEventListener(new GuildJoinLeaveEvent());
        TwilioMessageEvent tme = new TwilioMessageEvent();
        tme.run();
        boolean guildFound = false;
        for (Guild g : jda.getGuilds()) {
            if (g.getId().compareToIgnoreCase(settings.getGuild()) == 0) {
                TwilioBot.sendSystemMessageToSMS("Bot is now running.");
                guildFound = true;
                break;
            }
        }
        if (!guildFound) {
            TwilioBot.sendSystemMessageToSMS("Bot is now running, but we are not in our defined guild! (Or the guild was just never properly set in the config.)");
        }


        Runtime.getRuntime().addShutdownHook(new Thread(() -> TwilioBot.sendSystemMessageToSMS("Bot is now shutting down.")));
    }
}
