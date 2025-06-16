package com.fromzero.chatservice.domain.model.aggregates;

import com.fromzero.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chats")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Chat extends AuditableAbstractAggregateRoot<Chat> {

    @Column (nullable = false, unique = true)
    private UUID projectId;

    @Column (nullable = false)
    private UUID user1Id;

    @Column (nullable = false)
    private UUID user2Id;

    @Column (nullable = false)
    private boolean closed = false;

    @Column (nullable = false)
    private Instant createdAt;
}
