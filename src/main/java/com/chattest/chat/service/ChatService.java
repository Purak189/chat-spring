package com.chattest.chat.service;

import com.chattest.chat.entity.Chat;
import com.chattest.chat.repository.ChatRepository;

public class ChatService {

    private final ChatRepository chatRepository;

    public ChatService(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
}
