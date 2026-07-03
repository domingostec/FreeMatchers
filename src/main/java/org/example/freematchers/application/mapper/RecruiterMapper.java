package org.example.freematchers.application.mapper;

import org.example.freematchers.shared.dto.request.RecruiterRequest;
import org.example.freematchers.shared.dto.response.RecruiterResponse;
import org.example.freematchers.domain.model.Recruiter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecruiterMapper {
    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "enterprise", source = "enterprise")
    RecruiterResponse recruiterToRecruiterResponse(Recruiter recruiter);

    @Mapping(target = "name", source = "name")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "enterprise", source = "enterprise")
    Recruiter recruiterRequestToRecruiter(RecruiterRequest request);
}



