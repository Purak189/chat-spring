package com.fromzero.chatservice.domain.model.aggregates;

import com.chattest.chat.entity.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fromzero.shared.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "chatMessage")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage extends AuditableAbstractAggregateRoot<ChatMessage> {

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String sender;

    @Column(nullable = false)
    private UUID projectId;

    @Column(nullable = false)
    @JsonProperty("senderId")
    private UUID senderId;

    @Column(nullable = false)
    private MessageType type;

    @Column(nullable = false)
    private Instant timestamp;

    public ChatMessage(UUID senderId, String sender, String s, String join) {
        this.senderId = senderId;
        this.sender = sender;
        this.content = s;
        this.type = MessageType.valueOf(join);
        this.timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return "ChatMessage{" +
                "senderId=" + senderId +
                ", sender='" + sender + '\'' +
                ", content='" + content + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
