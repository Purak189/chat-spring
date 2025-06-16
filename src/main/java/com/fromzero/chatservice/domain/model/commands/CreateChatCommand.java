package com.fromzero.chatservice.domain.model.commands;

import java.util.UUID;

public record CreateChatCommand(UUID projectId, UUID user1, UUID user2) {
}
