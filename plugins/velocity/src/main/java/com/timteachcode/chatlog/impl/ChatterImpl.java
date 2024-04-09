package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.IChatter;
import com.velocitypowered.api.proxy.Player;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ChatterImpl implements IChatter {
    private final UUID uniqueId;
    private final String name;

    public ChatterImpl(Player player) {
        this(player.getUniqueId(), player.getUsername());
    }
}
