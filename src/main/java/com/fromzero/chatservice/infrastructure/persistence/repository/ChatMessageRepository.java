package com.fromzero.chatservice.infrastructure.persistence.repository;

import com.fromzero.chatservice.domain.model.aggregates.ChatMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, UUID>, PagingAndSortingRepository<ChatMessage, UUID> {
    Page<ChatMessage> findByProjectIdOrderByTimestampDesc(UUID projectId, Pageable pageable);
}
