package com.chattest.chat.service;

import com.chattest.chat.entity.Chat;
import com.chattest.chat.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Chat createChat(UUID projectId, UUID user1Id, UUID user2Id) {
        Chat chat = Chat.builder()
                .projectId(projectId)
                .user1Id(user1Id)
                .user2Id(user2Id)
                .createdAt(Instant.now())
                .closed(false)
                .build();

        return chatRepository.save(chat);
    }

    public boolean validateUserAndProject(UUID userId, UUID projectId) {
        return chatRepository.existsByProjectId(projectId)
                && (chatRepository.existsByUser1IdAndProjectId(userId, projectId)
                || chatRepository.existsByUser2IdAndProjectId(userId, projectId));
    }
}
