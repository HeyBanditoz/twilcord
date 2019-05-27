package io.banditoz.twilcord;

import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class GuildJoinLeaveEvent extends ListenerAdapter {
    private String guild;

    public GuildJoinLeaveEvent() {
        guild = SettingsManager.getInstance().getSettings().getGuild();
    }

    public void onGuildLeave(GuildLeaveEvent e) {
        new GuildJoinLeaveEvent();
        if (e.getGuild().getId().compareToIgnoreCase(this.guild) == 0) {
            TwilioBot.sendSystemMessageToSMS("The bot has just left the guild we are bridged to! (Possibly due to a ban, some strange error, or by free will.) You will not be able to receive or send any messages.");
        }

    }

    public void onGuildJoin(GuildJoinEvent e) {
        new GuildJoinLeaveEvent();
        if (e.getGuild().getId().compareToIgnoreCase(this.guild) == 0) {
            TwilioBot.sendSystemMessageToSMS("We are back in our bridged guild.");
        }
    }
}
