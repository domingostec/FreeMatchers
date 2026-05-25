package org.example.freematchers.mapper;

import org.example.freematchers.dto.request.RecruiterRequest;
import org.example.freematchers.dto.response.RecruiterResponse;
import org.example.freematchers.model.Recruiter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecruiterMapper {

    RecruiterResponse recruiterToRecruiterResponse(Recruiter recruiter);
    Recruiter recruiterResquestToRecruiter(RecruiterRequest request);

}
