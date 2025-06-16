package com.fromzero.chatservice.domain.model.queries;

import java.util.UUID;

public record PaginatedChatMessageQuery(UUID chatId, int page, int size) {
}
