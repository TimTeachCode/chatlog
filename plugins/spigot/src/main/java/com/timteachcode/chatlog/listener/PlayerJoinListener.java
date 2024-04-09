package com.timteachcode.chatlog.listener;

import com.timteachcode.chatlog.store.MessageData;
import com.timteachcode.chatlog.store.MessageStore;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {
    private final MessageStore messageStore;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        messageStore.saveMessage(MessageData.MessageIssuer.SYSTEM, null, null, String.format("%s hat das Spiel betreten.", event.getPlayer().getName()));
    }
}
