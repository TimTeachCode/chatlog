package com.timteachcode.chatlog;

import com.timteachcode.chatlog.impl.ChatterImpl;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.util.UUID;

public class ChatLogCommand extends Command {
    private final ChatLogPlugin plugin;
    private final String chatLogUrl;

    public ChatLogCommand(ChatLogPlugin plugin) {
        super("chatlog", null, "cl");
        this.plugin = plugin;
        chatLogUrl = plugin.getConfig().getString("url");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if (!(commandSender instanceof ProxiedPlayer player)) {
            commandSender.sendMessage(a("Du musst ein Spieler sein."));
            return;
        }

        if (args.length == 0) {
            player.sendMessage(a("Du musst einen Spieler angeben."));
            return;
        }

        ProxiedPlayer target = plugin.getProxy().getPlayer(args[0]);
        if (target == null) {
            UUID uniqueId = plugin.getChatLog().getMessageStore().uniqueIdFrom(args[0]);
            if (uniqueId == null || !plugin.getChatLog().getMessageStore().hasMessageFrom(uniqueId)) {
                player.sendMessage(a("Dieser Spieler hat in letzter Zeit keine Nachrichten verschickt."));
            } else {
                String name = plugin.getChatLog().getMessageStore().nameFrom(uniqueId);
                createChatLog(new ChatterImpl(uniqueId, name), player);
            }
        } else {
            if (!plugin.getChatLog().getMessageStore().hasMessageFrom(target.getUniqueId())) {
                player.sendMessage(a("Dieser Spieler hat in letzter Zeit keine Nachrichten verschickt."));
                return;
            }

            createChatLog(new ChatterImpl(target), player);
        }
    }

    private BaseComponent a(String message) {
        return TextComponent.fromLegacy(message);
    }

    private void createChatLog(IChatter reported, ProxiedPlayer reporter) {
        plugin.getChatLog().createChatLog(reported, new ChatterImpl(reporter), created -> {
            if (created.isSuccessful()) {
                reporter.sendMessage(a(String.format("Du hast einen ChatLog erstellt: %s", String.format(chatLogUrl, created.getId()))));
            } else {
                reporter.sendMessage(a(String.format("Fehler beim Erstellen vom ChatLog: %s", created.getErrorType().name())));
            }
        });
    }
}
