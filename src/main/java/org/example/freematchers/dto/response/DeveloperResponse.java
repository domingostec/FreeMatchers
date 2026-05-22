package org.example.freematchers.dto.response;

import java.util.List;

public record DeveloperResponse(

        String name,
        String email,
        Integer workload,

        List<String> skills
){}
