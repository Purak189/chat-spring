package com.fromzero.chatservice.domain.services;

import com.fromzero.chatservice.domain.model.aggregates.ChatMessage;
import com.fromzero.chatservice.domain.model.commands.CreateChatMessageCommand;

import java.util.Optional;

public interface ChatMessageCommandService {
    Optional<ChatMessage> handle(CreateChatMessageCommand createChatMessageCommand);
}
