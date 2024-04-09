package com.timteachcode.chatlog.listener;

import com.timteachcode.chatlog.store.MessageData;
import com.timteachcode.chatlog.store.MessageStore;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

@RequiredArgsConstructor
public class AsyncPlayerChatListener implements Listener {
    private final MessageStore messageStore;

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        messageStore.saveMessage(MessageData.MessageIssuer.USER, event.getPlayer().getUniqueId(), event.getPlayer().getName(), event.getMessage());
    }
}
