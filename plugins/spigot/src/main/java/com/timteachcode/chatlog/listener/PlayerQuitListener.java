package com.timteachcode.chatlog.listener;

import com.timteachcode.chatlog.store.MessageData;
import com.timteachcode.chatlog.store.MessageStore;
import lombok.RequiredArgsConstructor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {
    private final MessageStore messageStore;

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        messageStore.saveMessage(MessageData.MessageIssuer.SYSTEM, null, null, String.format("%s hat das Spiel verlassen.", event.getPlayer().getName()));
    }
}
