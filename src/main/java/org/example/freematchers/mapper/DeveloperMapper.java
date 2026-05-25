package org.example.freematchers.mapper;

import org.example.freematchers.dto.request.DeveloperRequest;
import org.example.freematchers.dto.response.DeveloperResponse;
import org.example.freematchers.model.Developer;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED
)
public interface DeveloperMapper {

    DeveloperMapper INSTANCE = Mappers.getMapper(DeveloperMapper.class);
    DeveloperResponse developerToDeveloperResponse(Developer developer);
    Developer developerRequestToDeveloper(DeveloperRequest developerRequest);
    void updateDeveloperFromRequest(DeveloperRequest request, @MappingTarget Developer developer);
    void updateSkillsFromRequest(DeveloperRequest request, @MappingTarget Developer developer);
}
