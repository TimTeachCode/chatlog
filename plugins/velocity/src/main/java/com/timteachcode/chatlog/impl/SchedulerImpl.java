package com.timteachcode.chatlog.impl;

import com.timteachcode.chatlog.ChatLogPlugin;
import com.timteachcode.chatlog.IScheduler;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SchedulerImpl implements IScheduler {
    private final ChatLogPlugin plugin;

    @Override
    public void runAsync(Runnable runnable) {
        plugin.getServer().getScheduler().buildTask(plugin, runnable).schedule();
    }
}
