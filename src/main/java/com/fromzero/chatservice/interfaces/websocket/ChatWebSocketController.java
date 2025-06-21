package com.fromzero.chatservice.interfaces.websocket;

import com.fromzero.chatservice.domain.model.aggregates.ChatMessage;
import com.fromzero.chatservice.domain.services.ChatMessageCommandService;
import com.fromzero.chatservice.domain.services.ChatMessageQueryService;
import com.fromzero.chatservice.domain.services.ChatQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@Controller
public class ChatWebSocketController {
    private final ChatMessageCommandService chatMessageCommandService;
    private final ChatMessageQueryService chatMessageQueryService;
    private final ChatQueryService chatQueryService;

    public ChatWebSocketController(ChatMessageCommandService chatMessageCommandService, ChatMessageQueryService chatMessageQueryService, ChatQueryService chatQueryService) {
        this.chatMessageCommandService = chatMessageCommandService;
        this.chatMessageQueryService = chatMessageQueryService;
        this.chatQueryService = chatQueryService;
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage.{projectId}")
    @SendTo("/topic/project/{projectId}")
    public ChatMessage sendMessage(
            @DestinationVariable UUID projectId,
            @Payload ChatMessage chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.join.{projectId}")
    public void joinRoom(
            @DestinationVariable UUID projectId,
            @Payload ChatMessage joinMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        UUID userId = joinMessage.getSenderId();
    }
}
