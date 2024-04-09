package com.timteachcode.chatlog;

import com.timteachcode.chatlog.impl.ChatterImpl;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class ChatLogCommand implements SimpleCommand {
    private final ProxyServer server;
    private final ChatLog chatLog;
    private final String chatLogUrl;

    @Override
    public void execute(Invocation invocation) {
        CommandSource source = invocation.source();
        String[] args = invocation.arguments();

        if (!(source instanceof Player player)) {
            source.sendPlainMessage("Du musst ein Spieler sein.");
            return;
        }

        if (args.length == 0) {
            player.sendPlainMessage("Du musst einen Spieler angeben.");
            return;
        }

        Player target = server.getPlayer(args[0]).orElse(null);
        if (target == null) {
            UUID uniqueId = chatLog.getMessageStore().uniqueIdFrom(args[0]);
            if (uniqueId == null || !chatLog.getMessageStore().hasMessageFrom(uniqueId)) {
                player.sendPlainMessage("Dieser Spieler hat in letzter Zeit keine Nachrichten verschickt.");
            } else {
                String name = chatLog.getMessageStore().nameFrom(uniqueId);
                createChatLog(new ChatterImpl(uniqueId, name), player);
            }
        } else {
            if (!chatLog.getMessageStore().hasMessageFrom(target.getUniqueId())) {
                player.sendPlainMessage("Dieser Spieler hat in letzter Zeit keine Nachrichten verschickt.");
                return;
            }

            createChatLog(new ChatterImpl(target), player);
        }
    }

    private void createChatLog(IChatter reported, Player reporter) {
        chatLog.createChatLog(reported, new ChatterImpl(reporter), created -> {
            if (created.isSuccessful()) {
                reporter.sendPlainMessage(String.format("Du hast einen ChatLog erstellt: %s", String.format(chatLogUrl, created.getId())));
            } else {
                reporter.sendPlainMessage(String.format("Fehler beim Erstellen des ChatLogs: %s", created.getErrorType().name()));
            }
        });
    }
}
