package com.timteachcode.chatlog;

import com.timteachcode.chatlog.impl.DatabaseCredentialsImpl;
import com.timteachcode.chatlog.impl.LogHandlerImpl;
import com.timteachcode.chatlog.impl.MessageStoreConfigurationImpl;
import com.timteachcode.chatlog.impl.SchedulerImpl;
import com.timteachcode.chatlog.listener.AsyncPlayerChatListener;
import com.timteachcode.chatlog.listener.PlayerJoinListener;
import com.timteachcode.chatlog.listener.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;

public class ChatLogPlugin extends JavaPlugin {
    private static final long ONE_MINUTE_TICKS = 20L * 60;
    private ChatLog chatLog;

    @Override
    public void onEnable() {
        setupConfig();

        LogHandlerImpl logHandler = new LogHandlerImpl(getLogger());
        SchedulerImpl scheduler = new SchedulerImpl(this);
        DatabaseCredentialsImpl databaseCredentials = new DatabaseCredentialsImpl(getConfig());
        MessageStoreConfigurationImpl messageStoreConfiguration = new MessageStoreConfigurationImpl(getConfig());

        chatLog = ChatLog.init(logHandler, scheduler, databaseCredentials);
        Bukkit.getScheduler().runTaskTimer(this, chatLog.createMessageStore(messageStoreConfiguration), ONE_MINUTE_TICKS, ONE_MINUTE_TICKS);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new AsyncPlayerChatListener(chatLog.getMessageStore()), this);
        pluginManager.registerEvents(new PlayerJoinListener(chatLog.getMessageStore()), this);
        pluginManager.registerEvents(new PlayerQuitListener(chatLog.getMessageStore()), this);

        Objects.requireNonNull(getCommand("chatlog")).setExecutor(new ChatLogCommand(chatLog, getConfig().getString("url")));
    }

    @Override
    public void onDisable() {
        chatLog.shutdown();
    }

    private void setupConfig() {
        FileConfiguration config = getConfig();

        config.addDefault("db_credentials.hostname", "localhost");
        config.addDefault("db_credentials.port", 3306);
        config.addDefault("db_credentials.database", "chatlog");
        config.addDefault("db_credentials.username", "chatlog");
        config.addDefault("db_credentials.password", "password");

        config.addDefault("message_store.expiration", 15);
        config.addDefault("message_store.max", 50);

        config.addDefault("url", "http://localhost:5173/%s");

        config.options().copyDefaults(true);

        try {
            if (!getDataFolder().exists() && !getDataFolder().mkdir()) {
                getLogger().severe("Could not create data folder");
                return;
            }
            config.save(new File(getDataFolder(), "config.yml"));
        } catch (IOException e) {
            getLogger().log(Level.SEVERE, "Could not copy default config", e);
        }
    }
}
