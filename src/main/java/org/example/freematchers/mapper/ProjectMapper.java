package org.example.freematchers.mapper;

import org.example.freematchers.dto.request.ProjectRequest;
import org.example.freematchers.dto.response.ProjectResponse;
import org.example.freematchers.model.Projects;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProjectMapper {

    ProjectResponse projectToProjectResponse(Projects projects);
    Projects projectRequestToProject(ProjectRequest request);
    List<ProjectResponse> toResponseList(List<Projects> projects);
    void updateProjectFromRequest(ProjectRequest request, @MappingTarget Projects project);
}
