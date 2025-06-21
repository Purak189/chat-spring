package com.fromzero.chatservice.interfaces.rest.transform;

import com.fromzero.chatservice.domain.model.aggregates.Chat;
import com.fromzero.chatservice.interfaces.rest.resources.CreateChatResource;

public class CreateChatResourceFromEntityAssembler {
    public static CreateChatResource toResourceFromEntity(Chat entity) {
        return new CreateChatResource(entity.getProjectId(), entity.getUser1Id(), entity.getUser2Id());
    }
}
