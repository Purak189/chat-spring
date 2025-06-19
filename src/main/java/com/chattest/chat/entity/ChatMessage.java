package com.chattest.chat.entity;

import com.chattest.chat.entity.enums.MessageType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fromzero.chatservice.domain.model.commands.CreateChatMessageCommand;
import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

    private String content;
    private String sender;
    private UUID projectId;
    @JsonProperty("senderId")
    private UUID senderId;
    private MessageType type;
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
