package org.example.freematchers.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.AllocationRequest;
import org.example.freematchers.dto.response.AllocationResponse;
import org.example.freematchers.dto.response.MatchResultResponse;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.exceptions.LackRequiredHoursException;
import org.example.freematchers.exceptions.ProjectInactiveException;
import org.example.freematchers.model.Allocation;
import org.example.freematchers.model.Developer;
import org.example.freematchers.model.Projects;
import org.example.freematchers.repository.AllocationRepository;
import org.example.freematchers.repository.DeveloperMatchProjection;
import org.example.freematchers.repository.DeveloperRepository;
import org.example.freematchers.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllocationService {

    private final AllocationRepository allocationRepository;
    private  final ProjectRepository projectRepository;
    private final DeveloperRepository developerRepository;

    private AllocationResponse toResponse(Allocation allocation){
        return new AllocationResponse(
                allocation.getId(),
                allocation.getDeveloper().getName(),
                allocation.getProject().getTitle(),
                allocation.getProject().getRecruiter().getName(),
                allocation.getProject().getRequiredHours(),
                allocation.getAllocationDate(),
                allocation.getStatusAllocation()
        );
    }

    @Transactional(readOnly = true)
    public List<MatchResultResponse> findMatchesForProjects(Long projectId) {
        Projects project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IdNotFoundException("Project Not Found"));

        if (!project.getStatus()) {
            throw new ProjectInactiveException("The position has already been filled.");
        }

        Set<String> projectSkillsLower = project.getProjectSkills().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if (projectSkillsLower.isEmpty()) {
            return Collections.emptyList();
        }

        List<DeveloperMatchProjection> candidates = developerRepository.findTopCandidates(
                project.getRequiredHours(),
                projectSkillsLower,
                projectSkillsLower.size()
        );

        return candidates.stream()
                .map(candidate -> {
                    Developer dev = developerRepository.findById(candidate.getId()).orElseThrow();

                    List<String> matchingSkills = project.getProjectSkills().stream()
                            .filter(skill -> dev.getSkills().stream()
                                    .anyMatch(devSkill -> devSkill.equalsIgnoreCase(skill)))
                            .toList();

                    return new MatchResultResponse(
                            candidate.getId(),
                            candidate.getName(),
                            candidate.getMatchPercentage(),
                            candidate.getFreeHours(),
                            matchingSkills
                    );
                })
                .toList();
    }

    @Transactional
    public AllocationResponse bindDeveloperToProject(AllocationRequest allocationRequest) {
        Developer developer = developerRepository.findById(allocationRequest.developerId())
                .orElseThrow(() -> new IdNotFoundException("ERROR! Developer ID not found"));

        Projects projects = projectRepository.findById(allocationRequest.projectId())
                .orElseThrow(() -> new IdNotFoundException("ERROR! Project ID not found"));

        if (!projects.getStatus()) {
            throw new ProjectInactiveException("ERROR! this project is inactive or already closed");
        }

        Integer freeHours = calculateFreehours(developer);
        if (freeHours < projects.getRequiredHours()) {
            throw new LackRequiredHoursException("ERROR! Developer does not have enough available hours. Available: " + freeHours);
        }

        Allocation allocation = new Allocation();
        allocation.setDeveloper(developer);
        allocation.setProject(projects);
        allocation.setAllocationDate(java.time.LocalDate.now());
        allocation.setStatusAllocation("ACTIVE");

        Allocation savedAllocation = allocationRepository.save(allocation);
        return toResponse(savedAllocation);
    }

    public void projectCompletion(Long allocationId){

        Allocation allocation = allocationRepository.findById(allocationId)
                .orElseThrow(() -> new LackRequiredHoursException("ERROR! Allocation record not found"));

        if("FINISHED".equals(allocation.getStatusAllocation())){
            throw new LackRequiredHoursException("ERROR!This allocation has already been finished");
        }

        allocation.setStatusAllocation("FINISHED");

        allocationRepository.save(allocation);

    }

    private Integer calculateFreehours(Developer developer) {
        int totalWorkload = developer.getWorkload() != null ? developer.getWorkload() : 40;

        Integer busyHours = allocationRepository.sumOccupiedHoursByDeveloperId(developer.getId());

        return totalWorkload - busyHours;
    }
}
