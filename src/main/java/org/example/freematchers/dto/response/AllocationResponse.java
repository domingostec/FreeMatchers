package org.example.freematchers.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public record AllocationResponse(

    Long allocationId,

    String developerName,

    String projectTitle,

    String recruiterName,

    Integer hoursAllocated,

    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate allocationDate,

    String status
){}
