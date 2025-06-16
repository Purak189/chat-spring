package com.fromzero.chatservice.infrastructure.persistence.repository;

import com.fromzero.chatservice.domain.model.aggregates.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ChatRepository extends JpaRepository<Chat, UUID> {
    Optional<Chat> findByProjectId(UUID projectId);
    Boolean existsByProjectId(UUID projectId);

    boolean existsByUser1IdAndProjectId(UUID userId, UUID projectId);
    boolean existsByUser2IdAndProjectId(UUID userId, UUID projectId);
}
