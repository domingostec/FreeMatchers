package org.example.freematchers.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.application.mapper.ProjectMapper;
import org.example.freematchers.domain.port.in.ProjectUseCase;
import org.example.freematchers.shared.dto.request.ProjectRequest;
import org.example.freematchers.shared.dto.response.ProjectResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private  final ProjectUseCase projectUseCase;
    private final ProjectMapper projectMapper;

    @PostMapping
    public ResponseEntity<ProjectResponse> creatingProject(@Valid @RequestBody ProjectRequest request){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectMapper.projectToProjectResponse(
                        projectUseCase.createProject(
                                projectMapper.projectRequestToProject(request))
                ));
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<ProjectResponse>> getProjects(@PathVariable Long recruiterId){
        return ResponseEntity.ok(
                projectMapper.projectToProjectResponse(
                        projectUseCase.getProjectByRecruiterId(recruiterId)
                )
        );
    }

    @GetMapping("/Project/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id){
        return ResponseEntity.ok(
                projectMapper.projectToProjectResponse(
                        projectUseCase.getProjectById(id)
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@Valid @RequestBody ProjectRequest request, @PathVariable Long id){
        return ResponseEntity.ok(
                projectMapper.projectToProjectResponse(
                        projectUseCase.updateProjectById(
                                projectMapper.projectRequestToProject(request), id)
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable  Long id){
        projectUseCase.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }
}
