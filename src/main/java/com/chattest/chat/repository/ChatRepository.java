package com.chattest.chat.repository;

import com.chattest.chat.entity.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Optional<Chat> findByProjectId(UUID projectId);
    Boolean existsByProjectId(UUID projectId);

    boolean existsByUser1IdAndProjectId(UUID userId, UUID projectId);
    boolean existsByUser2IdAndProjectId(UUID userId, UUID projectId);
}
