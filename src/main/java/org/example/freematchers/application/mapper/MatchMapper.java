package org.example.freematchers.application.mapper;

import org.example.freematchers.shared.dto.response.MatchResultResponse;
import org.example.freematchers.domain.model.Developer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {

    @Mapping(target = "developerId", source = "developer.id")
    @Mapping(target = "developerName", source = "developer.name")
    @Mapping(target = "availableHours", source = "developer.workload")
    @Mapping(target = "matchPercentage", expression = "java(calculateMatchPercentage(projectSkills, developer.getSkills()))")
    @Mapping(target = "matchingSkills", expression = "java(calculateMatchingSkills(projectSkills, developer.getSkills()))")
    MatchResultResponse toMatchResponse(Developer developer, List<String> projectSkills);


    default Double calculateMatchPercentage(List<String> projectSkills, List<String> devSkills) {
        if (projectSkills == null || projectSkills.isEmpty()) return 0.0;
        long matching = projectSkills.stream()
                .filter(skill -> devSkills.stream().anyMatch(devSkill -> devSkill.equalsIgnoreCase(skill)))
                .count();
        return (matching * 100.0) / projectSkills.size();
    }

    default List<String> calculateMatchingSkills(List<String> projectSkills, List<String> devSkills) {
        if (projectSkills == null || devSkills == null) return List.of();
        return projectSkills.stream()
                .filter(skill -> devSkills.stream().anyMatch(devSkill -> devSkill.equalsIgnoreCase(skill)))
                .toList();
    }
}


