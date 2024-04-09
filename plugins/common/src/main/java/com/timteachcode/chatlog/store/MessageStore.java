package com.timteachcode.chatlog.store;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class MessageStore {
    private static final List<MessageData> data = new CopyOnWriteArrayList<>();

    private final IMessageStoreConfiguration config;

    public void saveMessage(MessageData.MessageIssuer issuer, UUID uniqueId, String name, String message) {
        data.add(new MessageData(issuer, uniqueId, name, message, System.currentTimeMillis()));
    }

    public boolean hasMessageFrom(UUID uniqueId) {
        return lastMessages().stream().anyMatch((messageData -> messageData.getUniqueId() != null && messageData.getUniqueId().equals(uniqueId)));
    }

    public UUID uniqueIdFrom(String name) {
        Optional<MessageData> optional = data.stream().filter((filterData -> filterData.getName().equalsIgnoreCase(name))).findFirst();
        return optional.map(MessageData::getUniqueId).orElse(null);
    }

    public String nameFrom(UUID uniqueId) {
        Optional<MessageData> optional = data.stream().filter((filterData -> filterData.getUniqueId().equals(uniqueId))).findFirst();
        return optional.map(MessageData::getName).orElse(null);
    }

    public List<MessageData> lastMessages() {
        List<MessageData> returnData = new ArrayList<>();
        for (int i = Math.max(data.size() - config.maxMessages() - 1, 0); i < data.size(); i++) {
            MessageData messageData = data.get(i);
            if (!messageData.isExpired(config.expirationTime())) returnData.add(messageData);
        }

        return returnData;
    }

    public Runnable scheduleTask() {
        return () -> data.removeIf((messageData -> messageData.isExpired(config.expirationTime())));
    }
}
