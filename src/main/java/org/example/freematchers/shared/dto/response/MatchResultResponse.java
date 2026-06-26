package org.example.freematchers.shared.dto.response;

import java.util.List;

public record MatchResultResponse(
        Long developerId,
        String developerName,
        Double matchPercentage,
        Integer availableHours,
        List<String> matchingSkills
) {
}
