package org.example.freematchers.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record ProjectRequest(

        @NotBlank(message = "ERROR! Title canot be null")
        String title,

        @NotBlank(message = "ERROR! Description canot be null")
        String description,

        @Min(20)
        Integer requiredHours,

        List<String> projectSkills,

        Boolean status,

        Long recruiterId
) {}
