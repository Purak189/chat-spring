package com.fromzero.chatservice.domain.model.aggregates;

import com.fromzero.chatservice.domain.model.commands.CreateChatCommand;
import com.fromzero.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chats")
@NoArgsConstructor
@Getter
@Setter
public class Chat extends AuditableAbstractAggregateRoot<Chat> {

    @Column (columnDefinition = "UUID", nullable = false, unique = true)
    private UUID projectId;

    @Column (columnDefinition = "UUID", nullable = false)
    private UUID user1Id;

    @Column (columnDefinition = "UUID", nullable = false)
    private UUID user2Id;

    @Column (nullable = false)
    private boolean closed = false;

    public Chat(CreateChatCommand command) {
        this.projectId = command.projectId();
        this.user1Id = command.user1();
        this.user2Id = command.user2();
        this.closed = false;
    }
}
