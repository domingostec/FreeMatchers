package org.example.freematchers.dto.response;

import java.util.List;

public record ProjectResponse (

        Long id,

        String title,

        String description,

        Integer requiredHours,

        List<String> projectSkills,

        Boolean status,

        Long recruiterId,

        String recruiterName

){}
