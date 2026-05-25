package org.example.freematchers.mapper;

import org.example.freematchers.dto.response.MatchResultResponse;
import org.example.freematchers.model.Developer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "id", source = "developer.id")
    @Mapping(target = "name", source = "developer.name")
    @Mapping(target = "freeHours", source = "developer.workload")
    @Mapping(target = "matchPercentage", source = "matchPercentage")
    @Mapping(target = "matchingSkills", expression = "java(calculateMatchingSkills(projectSkills, developer.getSkills()))")
    MatchResultResponse toMatchResponse(Developer developer, Double matchPercentage, List<String> projectSkills);

    default List<String> calculateMatchingSkills(List<String> projectSkills, List<String> devSkills) {
        if (projectSkills == null || devSkills == null) return List.of();

        return projectSkills.stream()
                .filter(skill -> devSkills.stream()
                        .anyMatch(devSkill -> devSkill.equalsIgnoreCase(skill)))
                .toList();
    }
}
