package com.timteachcode.chatlog.listener;

import com.timteachcode.chatlog.store.MessageData;
import com.timteachcode.chatlog.store.MessageStore;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.PlayerChatEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PlayerChatListener {
    private final MessageStore messageStore;

    @Subscribe
    public void onPlayerChat(PlayerChatEvent event) {
        messageStore.saveMessage(MessageData.MessageIssuer.USER, event.getPlayer().getUniqueId(), event.getPlayer().getUsername(), event.getMessage());
    }
}
