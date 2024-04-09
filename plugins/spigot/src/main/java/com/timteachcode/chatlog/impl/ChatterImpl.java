package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.IChatter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class ChatterImpl implements IChatter {
    private final UUID uniqueId;
    private final String name;

    public ChatterImpl(Player player) {
        this(player.getUniqueId(), player.getName());
    }
}
