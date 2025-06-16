package com.fromzero.chatservice.domain.services;

import com.fromzero.chatservice.domain.model.aggregates.ChatMessage;
import com.fromzero.chatservice.domain.model.queries.PaginatedChatMessageQuery;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.Optional;

public interface ChatMessageQueryService {
    Optional<Page<ChatMessage>> handle(PaginatedChatMessageQuery paginatedChatMessageQuery);
}
