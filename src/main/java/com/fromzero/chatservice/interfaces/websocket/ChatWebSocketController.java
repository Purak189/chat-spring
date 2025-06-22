package com.fromzero.chatservice.interfaces.websocket;

import com.fromzero.chatservice.domain.model.aggregates.ChatJoinMessage;
import com.fromzero.chatservice.domain.model.aggregates.ChatMessage;
import com.fromzero.chatservice.domain.model.commands.CreateChatMessageCommand;
import com.fromzero.chatservice.domain.model.queries.ValidateUserAndProject;
import com.fromzero.chatservice.domain.services.ChatMessageCommandService;
import com.fromzero.chatservice.domain.services.ChatMessageQueryService;
import com.fromzero.chatservice.domain.services.ChatQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.time.Instant;
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
        var chatMessageCommand = new CreateChatMessageCommand(
                chatMessage.getContent(),
                chatMessage.getSender(),
                projectId,
                chatMessage.getSenderId(),
                chatMessage.getType(),
                Instant.now()
        );

        if (!chatQueryService.handle(new ValidateUserAndProject(chatMessage.getSenderId(), projectId))) {
            log.error("User {} is not authorized to send messages in project {}", chatMessage.getSenderId(), projectId);
            throw new IllegalArgumentException("User is not authorized to send messages in this project");
        }

        this.chatMessageCommandService.handle(chatMessageCommand);

        return chatMessage;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return "An error occurred: " + exception.getMessage();
    }

    @MessageMapping("/chat.join.{projectId}")
    public void joinRoom(
            @DestinationVariable UUID projectId,
            @Payload ChatJoinMessage joinMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        log.info("Received JOIN message for project {}: {}", projectId, joinMessage);

        UUID userId = joinMessage.getSenderId();
        log.info("User ID: {}", userId);

        if (!chatQueryService.handle(new ValidateUserAndProject(userId, projectId))) {
            log.error("User {} is not authorized to join project {}", userId, projectId);
            throw new IllegalArgumentException("User is not authorized to join this project");
        }

        log.info("Sending message to /topic/project/{}: {}", projectId, joinMessage);

        messagingTemplate.convertAndSend(
                "/topic/project/" + projectId,
                new ChatJoinMessage(
                        joinMessage.getSenderId(),
                        joinMessage.getSender(),
                        joinMessage.getSender() + "se ha unido al chat."                )
        );

        log.info("User {} joined project {}", joinMessage.getSenderId(), projectId);
    }
}
