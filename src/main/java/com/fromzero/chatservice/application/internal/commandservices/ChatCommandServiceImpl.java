package com.fromzero.chatservice.application.internal.commandservices;

import com.fromzero.chatservice.domain.model.aggregates.Chat;
import com.fromzero.chatservice.domain.model.commands.CreateChatCommand;
import com.fromzero.chatservice.domain.services.ChatCommandService;
import com.fromzero.chatservice.infrastructure.persistence.repository.ChatRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatCommandServiceImpl implements ChatCommandService {
    private final ChatRepository chatRepository;

    public ChatCommandServiceImpl(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }

    @Override
    public Optional<Chat> handle(CreateChatCommand createChatCommand) {
        if (chatRepository.existsByProjectId(createChatCommand.projectId())) {
            return Optional.empty();
        }

        Chat chat = new Chat(createChatCommand);

        chatRepository.save(chat);
        return Optional.of(chat);
    }
}
