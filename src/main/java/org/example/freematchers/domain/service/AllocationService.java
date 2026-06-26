package org.example.freematchers.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.domain.port.in.AllocationUseCase;
import org.example.freematchers.domain.port.out.AllocationRepositoryPort;
import org.example.freematchers.domain.port.out.DeveloperRepositoryPort;
import org.example.freematchers.domain.port.out.ProjectRepositoryPort;
import org.example.freematchers.shared.exceptions.LackRequiredHoursException;
import org.example.freematchers.shared.exceptions.ProjectInactiveException;
import org.example.freematchers.domain.model.Allocation;
import org.example.freematchers.domain.model.Developer;
import org.example.freematchers.domain.model.Projects;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class AllocationService implements AllocationUseCase{

    private final AllocationRepositoryPort allocationReport;
    private  final ProjectRepositoryPort projectReport;
    private final DeveloperRepositoryPort developerReport;


    @Override
    public List<Allocation> findMatchesForProjects(Long projectId) {
        Projects project = projectReport.findById(projectId);

        if (!project.getStatus()) {
            throw new ProjectInactiveException("The position has already been filled.");
        }

        Set<String> projectSkillsLower = project.getProjectSkills().stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        if (projectSkillsLower.isEmpty()) {
            return Collections.emptyList();
        }

        List<Developer> candidates = developerReport.findTopCandidates(
                project.getRequiredHours(),
                projectSkillsLower,
                projectSkillsLower.size()
        );

        return candidates.stream()
                .map(dev -> {
                    Allocation allocation = new Allocation();
                    allocation.assignDeveloper(dev);
                    allocation.assignProject(project);
                    allocation.activate();
                    return allocation;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Allocation bindDeveloperToProject(Allocation allocation) {
        Developer developer = developerReport.findById(allocation.getDeveloper().getId());

        Projects projects = projectReport.findById(allocation.getProject().getId());

        if (!projects.getStatus()) {
            throw new ProjectInactiveException("ERROR! this project is inactive or already closed");
        }

        Integer freeHours = calculateFreehours(developer);
        if (freeHours < projects.getRequiredHours()) {
            throw new LackRequiredHoursException("ERROR! Developer does not have enough available hours. Available: " + freeHours);
        }

        allocation.assignDeveloper(developer);
        allocation.assignProject(projects);
        allocation.activate();

        return allocationReport .save(allocation);
    }

    @Override
    public void projectCompletion(Long allocationId){

        Allocation allocation = allocationReport .findById(allocationId);

        if(allocation.isFinished()){
            throw new LackRequiredHoursException("ERROR!This allocation has already been finished");
        }

        allocation.deactivate();

        allocationReport .save(allocation);

    }

    private Integer calculateFreehours(Developer developer) {
        int totalWorkload = Optional.ofNullable(developer.getWorkload()).orElse(40);
        Integer busyHours = allocationReport .sumOccupiedHoursByDeveloperId(developer.getId());

        return totalWorkload - busyHours;
    }
}