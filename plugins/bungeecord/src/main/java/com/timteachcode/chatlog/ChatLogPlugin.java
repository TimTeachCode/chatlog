package com.timteachcode.chatlog;

import com.timteachcode.chatlog.impl.DatabaseCredentialsImpl;
import com.timteachcode.chatlog.impl.LogHandlerImpl;
import com.timteachcode.chatlog.impl.MessageStoreConfigurationImpl;
import com.timteachcode.chatlog.impl.SchedulerImpl;
import com.timteachcode.chatlog.listener.ChatListener;
import lombok.Getter;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

@Getter
public class ChatLogPlugin extends Plugin {
    private Configuration config;
    private ChatLog chatLog;

    @Override
    public void onEnable() {
        setupConfig();

        LogHandlerImpl logHandler = new LogHandlerImpl(getLogger());
        SchedulerImpl scheduler = new SchedulerImpl(this);
        DatabaseCredentialsImpl databaseCredentials = new DatabaseCredentialsImpl(config);
        MessageStoreConfigurationImpl messageStoreConfiguration = new MessageStoreConfigurationImpl(config);

        chatLog = ChatLog.init(logHandler, scheduler, databaseCredentials);
        getProxy().getScheduler().schedule(this, chatLog.createMessageStore(messageStoreConfiguration), 1, 1, TimeUnit.MINUTES);

        getProxy().getPluginManager().registerListener(this, new ChatListener(chatLog));
        getProxy().getPluginManager().registerCommand(this, new ChatLogCommand(this));
    }

    @Override
    public void onDisable() {
        chatLog.shutdown();
    }

    private void setupConfig() {
        try {
            File file = new File(getDataFolder(), "config.yml");
            if (!file.exists()) {
                if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
                    getLogger().severe("Could not create config directories and files");
                    return;
                }

                try (InputStream inputStream = getResourceAsStream("config.yml")) {
                    Files.copy(inputStream, file.toPath());
                }
            }
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not create config file", e);
        }
    }
}
