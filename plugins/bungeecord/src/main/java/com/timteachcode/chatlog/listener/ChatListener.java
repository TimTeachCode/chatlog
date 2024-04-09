package com.timteachcode.chatlog.listener;

import com.timteachcode.chatlog.ChatLog;
import com.timteachcode.chatlog.store.MessageData;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

@RequiredArgsConstructor
public class ChatListener implements Listener {
    private final ChatLog chatLog;

    @EventHandler
    public void onChat(ChatEvent event) {
        if (!(event.getSender() instanceof ProxiedPlayer player) || event.isCommand()) return;

        chatLog.getMessageStore().saveMessage(MessageData.MessageIssuer.USER, player.getUniqueId(), player.getName(), event.getMessage());
    }
}
