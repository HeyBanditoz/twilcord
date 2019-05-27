package io.banditoz.twilcord;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.entities.Message.Attachment;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class DiscordMessageEvent extends ListenerAdapter {
    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        String message = e.getMessage().getContentDisplay();
        if (e.getChannel().getId().equals(SettingsManager.getInstance().getSettings().getChannel())
                && (e.getAuthor().getId().compareToIgnoreCase(Twilcord.jda.getSelfUser().getId())) != 0) {
            if (!e.getMessage().getAttachments().isEmpty()) { // if the list isn't empty...
                List<URI> attachmentList = new ArrayList<>();
                for (Attachment a : e.getMessage().getAttachments()) {
                    attachmentList.add(URI.create(a.getUrl()));
                }
                if (checkMessageLength(e)) return;
                TwilioBot.sendMessageToMMS(e, message, attachmentList);
                return;
            }
            if (checkMessageLength(e)) return;
            TwilioBot.sendMessageToSMS(e, message);
        }
    }

    private boolean checkMessageLength(MessageReceivedEvent e) {
        final int messageLength = e.getMessage().getContentDisplay().length();
        final int limit = 1527;
        if (messageLength >= limit) { // low value to account for trial message cancer and max username length (73 characters)
            TwilioBot.sendSystemMessageToSMS("Message sent by user <" + e.getAuthor().getName() + "> is greater than " + limit +  " characters! (contains " +  messageLength + " characters.) Message link:\n" + e.getMessage().getJumpUrl());
            TwilioBot.sendSystemMessageToDiscord("<@" + e.getAuthor().getId() + ">, your message was greater than " + limit + " characters! (contains " + messageLength + " characters.) Therefore, it was not delivered over the bridge.");
            return true;
        }
        return false;
    }
}
