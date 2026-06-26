package org.example.freematchers.application.mapper;

import org.example.freematchers.shared.dto.request.RecruiterRequest;
import org.example.freematchers.shared.dto.response.RecruiterResponse;
import org.example.freematchers.domain.model.Recruiter;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecruiterMapper {

    RecruiterResponse recruiterToRecruiterResponse(Recruiter recruiter);
    Recruiter recruiterResquestToRecruiter(RecruiterRequest request);

}
