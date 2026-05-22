package org.example.freematchers.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RecruiterRequest(


        @NotBlank(message = "ERROR! Name canot be null")
        String name,

        @NotBlank(message = "ERROR! Email canot be null")
        String email,

        @NotBlank(message = "ERROR! Password canot be null")
        String password,

        @NotBlank(message = "ERROR! Enterprise canot be null")
        String enterprise

) {}
