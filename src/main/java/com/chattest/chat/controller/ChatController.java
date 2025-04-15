package com.chattest.chat.controller;

import com.chattest.chat.entity.Chat;
import com.chattest.chat.entity.ChatMessage;
import com.chattest.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chats")
public class ChatController {

    private final ChatService chatService;

    @MessageMapping("/chat.sendMessage.{projectId}")
    @SendTo("/topic/project/{projectId}")
    public ChatMessage sendMessage(
            @DestinationVariable UUID projectId,
            @Payload ChatMessage chatMessage
    ){
        return chatMessage;
    }

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.join.{projectId}")
    public void joinRoom(
            @DestinationVariable UUID projectId,
            @Payload ChatMessage joinMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        UUID userId = joinMessage.getSenderId();

        if (!chatService.validateUserAndProject(userId, projectId)) {
            log.warn("Usuario NO autorizado: userId={}, projectId={}", userId, projectId);
            throw new IllegalArgumentException("Usuario no autorizado para este proyecto");
        }

        // Si pasa la validación, enviar confirmación
        messagingTemplate.convertAndSend(
                "/topic/project/" + projectId,
                new ChatMessage(
                        joinMessage.getSenderId(),
                        joinMessage.getSender(),
                        joinMessage.getSender() + " se ha unido al chat",
                        "JOIN"
                )
        );
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }


    @PostMapping("/create")
    public Chat createChat(
            @RequestParam UUID projectId,
            @RequestParam UUID user1Id,
            @RequestParam UUID user2Id) {
        return chatService.createChat(projectId, user1Id, user2Id);
    }
}
