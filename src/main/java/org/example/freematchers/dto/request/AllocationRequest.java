package org.example.freematchers.dto.request;

import jakarta.validation.constraints.NotNull;

public record AllocationRequest (
        @NotNull(message = "Developer ID is required")
        Long developerId,

        @NotNull(message = "Project ID is required")
        Long projectId
){
}
