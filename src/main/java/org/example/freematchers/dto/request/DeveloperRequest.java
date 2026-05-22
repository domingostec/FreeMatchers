package org.example.freematchers.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record DeveloperRequest(

        Long id,

        @NotBlank(message = "ERROR! Name canot be null")
        String name,

        @NotBlank(message = "ERROR! Email canot be null")
        String email,

        String password,

        @Min(20)
        Integer workload,

        List<String> skills
) {}
