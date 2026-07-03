package org.example.freematchers.application.mapper;

import org.example.freematchers.shared.dto.request.ProjectRequest;
import org.example.freematchers.shared.dto.response.ProjectResponse;
import org.example.freematchers.domain.model.Projects;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        collectionMappingStrategy = CollectionMappingStrategy.ADDER_PREFERRED)
public interface ProjectMapper {

    @Mapping(target = "recruiterId", source = "recruiter.id")
    @Mapping(target = "recruiterName", source = "recruiter.name")
    ProjectResponse projectToProjectResponse(Projects projects);

    Projects projectRequestToProject(ProjectRequest request);

    List<ProjectResponse> toResponseList(List<Projects> projects);

    void updateProjectFromRequest(ProjectRequest request, @MappingTarget Projects project);

    @Mapping(target = "recruiterId", source = "recruiter.id")
    @Mapping(target = "recruiterName", source = "recruiter.name")
    List<ProjectResponse> projectToProjectResponse(List<Projects> projects);
}

