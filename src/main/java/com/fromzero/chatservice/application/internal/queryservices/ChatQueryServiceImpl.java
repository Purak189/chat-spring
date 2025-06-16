package com.fromzero.chatservice.application.internal.queryservices;

import com.fromzero.chatservice.domain.model.aggregates.Chat;
import com.fromzero.chatservice.domain.model.queries.GetChatByIdQuery;
import com.fromzero.chatservice.domain.model.queries.GetChatByProjectIdQuery;
import com.fromzero.chatservice.domain.services.ChatQueryService;
import com.fromzero.chatservice.infrastructure.persistence.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatQueryServiceImpl implements ChatQueryService {
    private final ChatRepository chatRepository;

    public ChatQueryServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Optional<Chat> handle(GetChatByProjectIdQuery getChatByProjectIdQuery) {
        var chat = chatRepository.findByProjectId(getChatByProjectIdQuery.projectId());
        return chat;
    }
}
