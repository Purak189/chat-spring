package com.fromzero.chatservice.application.internal.commandservices;

import com.fromzero.chatservice.domain.model.aggregates.ChatMessage;
import com.fromzero.chatservice.domain.model.commands.CreateChatMessageCommand;
import com.fromzero.chatservice.domain.services.ChatMessageCommandService;
import com.fromzero.chatservice.infrastructure.persistence.repository.ChatMessageRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatMessageCommandServiceImpl implements ChatMessageCommandService {
    private final ChatMessageRepository chatMessageRepository;

    public ChatMessageCommandServiceImpl(ChatMessageRepository chatMessageRepository) {
        this.chatMessageRepository = chatMessageRepository;
    }

    @Override
    public Optional<ChatMessage> handle(CreateChatMessageCommand createChatMessageCommand) {
        ChatMessage chatMessage = new ChatMessage(createChatMessageCommand);

        chatMessageRepository.save(chatMessage);

        return Optional.of(chatMessage);
    }
}
