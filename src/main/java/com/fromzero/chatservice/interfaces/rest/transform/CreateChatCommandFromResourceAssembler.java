package com.fromzero.chatservice.interfaces.rest.transform;

import com.fromzero.chatservice.domain.model.commands.CreateChatCommand;
import com.fromzero.chatservice.interfaces.rest.resources.CreateChatResource;

public class CreateChatCommandFromResourceAssembler {
    public static CreateChatCommand toCommandFromResource(CreateChatResource createChatResource) {
        return new CreateChatCommand(createChatResource.projectId(), createChatResource.user1(), createChatResource.user2());
    }
}
