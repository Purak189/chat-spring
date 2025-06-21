package com.fromzero.chatservice.interfaces.rest;

import com.fromzero.chatservice.domain.services.ChatCommandService;
import com.fromzero.chatservice.domain.services.ChatQueryService;
import com.fromzero.chatservice.interfaces.rest.resources.CreateChatResource;
import com.fromzero.chatservice.interfaces.rest.transform.CreateChatCommandFromResourceAssembler;
import com.fromzero.chatservice.interfaces.rest.transform.CreateChatResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/api/v1/chats", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Chats", description = "Available chat endpoints")
public class ChatController {
    private final ChatQueryService chatQueryService;
    private final ChatCommandService chatCommandService;

    public ChatController(ChatQueryService chatQueryService, ChatCommandService chatCommandService) {
        this.chatQueryService = chatQueryService;
        this.chatCommandService = chatCommandService;
    }

    @PostMapping("/")
    public ResponseEntity<CreateChatResource> createChat(@RequestBody CreateChatResource createChatResource) {
        var createChatCommand = CreateChatCommandFromResourceAssembler.toCommandFromResource(createChatResource);
        var chat = this.chatCommandService.handle(createChatCommand);
        var chatResource = CreateChatResourceFromEntityAssembler.toResourceFromEntity(chat.get());

        return new ResponseEntity<>(chatResource, HttpStatus.CREATED);
    }

}
