package com.timteachcode.chatlog;

import com.timteachcode.chatlog.impl.ChatterImpl;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class ChatLogCommand implements CommandExecutor {
    private final ChatLog chatLog;
    private final String chatLogUrl;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String commandLabel, String[] args) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Du musst ein Spieler sein.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("Du musst einen Spieler angeben.");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            UUID uniqueId = chatLog.getMessageStore().uniqueIdFrom(args[0]);
            if (uniqueId == null || !chatLog.getMessageStore().hasMessageFrom(uniqueId)) {
                player.sendMessage("Dieser Spieler hat in letzter Zeit keine Nachrichten verschickt.");
                return true;
            } else {
                String name = chatLog.getMessageStore().nameFrom(uniqueId);
                createChatLog(new ChatterImpl(uniqueId, name), player);
                return true;
            }
        } else {
            if (!chatLog.getMessageStore().hasMessageFrom(target.getUniqueId())) {
                player.sendMessage("Dieser Spieler hat in letzter Zeit keine Nachrichten verschickt.");
                return true;
            }

            createChatLog(new ChatterImpl(target), player);
            return true;
        }
    }

    private void createChatLog(IChatter reported, Player reporter) {
        chatLog.createChatLog(reported, new ChatterImpl(reporter), created -> {
            if (created.isSuccessful()) {
                reporter.sendMessage(String.format("Du hast einen ChatLog erstellt: %s", String.format(chatLogUrl, created.getId())));
            } else {
                reporter.sendMessage(String.format("Fehler beim Erstellen vom ChatLog: %s", created.getErrorType().name()));
            }
        });
    }
}
