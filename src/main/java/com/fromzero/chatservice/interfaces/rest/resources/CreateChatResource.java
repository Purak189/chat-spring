package com.fromzero.chatservice.interfaces.rest.resources;

import java.util.UUID;

public record CreateChatResource(
        UUID projectId,
        UUID user1,
        UUID user2) {
}
