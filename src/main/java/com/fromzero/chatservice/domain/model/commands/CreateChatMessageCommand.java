package com.fromzero.chatservice.domain.model.commands;

import com.chattest.chat.entity.enums.MessageType;

import java.time.Instant;
import java.util.UUID;

public record CreateChatMessageCommand(
        String content,
        String sender,
        UUID projectId,
        UUID senderId,
        MessageType type,
        Instant timestamp
) {
}
