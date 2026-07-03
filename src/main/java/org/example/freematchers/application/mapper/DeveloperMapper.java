package org.example.freematchers.application.mapper;

import org.example.freematchers.shared.dto.request.DeveloperRequest;
import org.example.freematchers.shared.dto.response.DeveloperResponse;
import org.example.freematchers.domain.model.Developer;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface DeveloperMapper {
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "workload", source = "workload")
    @Mapping(target = "skills", source = "skills")
    DeveloperResponse developerToDeveloperResponse(Developer developer);

    Developer developerRequestToDeveloper(DeveloperRequest developerRequest);
}


