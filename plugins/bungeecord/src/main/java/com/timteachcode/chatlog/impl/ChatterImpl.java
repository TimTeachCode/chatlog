package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.IChatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ChatterImpl implements IChatter {
    private final UUID uniqueId;
    private final String name;

    public ChatterImpl(ProxiedPlayer player) {
        this(player.getUniqueId(), player.getName());
    }
}
