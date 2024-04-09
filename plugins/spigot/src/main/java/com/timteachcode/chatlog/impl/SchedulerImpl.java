package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.ChatLogPlugin;
import com.timteachcode.chatlog.IScheduler;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

@RequiredArgsConstructor
public class SchedulerImpl implements IScheduler {
    private final ChatLogPlugin plugin;

    @Override
    public void runAsync(Runnable runnable) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, runnable);
    }
}
