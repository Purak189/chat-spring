package com.fromzero.chatservice.domain.model.aggregates;

import com.fromzero.chatservice.domain.model.valueobjects.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class ChatJoinMessage {
    private String content;
    private String sender;
    private UUID senderId;
    private MessageType type;
    private Instant timestamp;

    public ChatJoinMessage(UUID senderId, String sender, String content) {
        this.senderId = senderId;
        this.content = content;
        this.sender = sender;
        this.type = MessageType.JOIN;
        this.timestamp = Instant.now();
    }

    @Override
    public String toString() {
        return "ChatJoinMessage{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", senderId=" + senderId +
                ", timestamp=" + timestamp +
                '}';
    }
}
