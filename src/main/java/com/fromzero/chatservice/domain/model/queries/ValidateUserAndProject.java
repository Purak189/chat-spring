package com.fromzero.chatservice.domain.model.queries;

import java.util.UUID;

public record ValidateUserAndProject(UUID userId, UUID projectId) {
}
