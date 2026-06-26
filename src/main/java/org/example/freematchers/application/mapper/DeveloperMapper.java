package org.example.freematchers.application.mapper;

import org.example.freematchers.shared.dto.request.DeveloperRequest;
import org.example.freematchers.shared.dto.response.DeveloperResponse;
import org.example.freematchers.domain.model.Developer;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface DeveloperMapper {

    DeveloperResponse developerToDeveloperResponse(Developer developer);
    Developer developerRequestToDeveloper(DeveloperRequest developerRequest);
    void updateDeveloperFromRequest(DeveloperRequest request, @MappingTarget Developer developer);
    void updateSkillsFromRequest(DeveloperRequest request, @MappingTarget Developer developer);
}
