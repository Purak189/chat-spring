package com.fromzero.chatservice.interfaces.rest;

import com.fromzero.chatservice.domain.model.aggregates.ChatMessage;
import com.fromzero.chatservice.domain.model.queries.PaginatedChatMessageQuery;
import com.fromzero.chatservice.domain.services.ChatMessageQueryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value="/api/v1/chat-messages", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Chat Messages", description = "Available chat message endpoints")
public class ChatMessageController {
    private final ChatMessageQueryService chatMessageQueryService;

    public ChatMessageController(ChatMessageQueryService chatMessageQueryService) {
        this.chatMessageQueryService = chatMessageQueryService;
    }

    @GetMapping("/messages/{projectId}")
    public ResponseEntity<Optional<Page<ChatMessage>>> getMessages(
            @PathVariable UUID projectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var chatMessageQuery = new PaginatedChatMessageQuery(projectId, page, size);
        var chatMessagesPage = this.chatMessageQueryService.handle(chatMessageQuery);

        return new ResponseEntity<>(chatMessagesPage, HttpStatus.OK);
    }
}
