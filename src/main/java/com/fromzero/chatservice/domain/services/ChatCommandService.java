package com.fromzero.chatservice.domain.services;

import com.fromzero.chatservice.domain.model.aggregates.Chat;
import com.fromzero.chatservice.domain.model.commands.CreateChatCommand;

import java.util.Optional;

public interface ChatCommandService {
    Optional<Chat> handle(CreateChatCommand createChatCommand);

}
