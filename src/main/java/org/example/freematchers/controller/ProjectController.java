package org.example.freematchers.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.ProjectRequest;
import org.example.freematchers.dto.response.ProjectResponse;
import org.example.freematchers.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {

    private  final ProjectService projectService;

    @PostMapping
    public ResponseEntity<ProjectResponse> creatingProject(@Valid @RequestBody ProjectRequest request){
        return ResponseEntity.ok(projectService.createProject(request));
    }

    @GetMapping("/recruiter/{recruiterId}")
    public ResponseEntity<List<ProjectResponse>> getProjects(@PathVariable Long recruiterId){
        return ResponseEntity.ok(projectService.getProjectByRecruiterId(recruiterId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(@Valid @RequestBody ProjectRequest request, Long id){
        return ResponseEntity.ok(projectService.updateProjectById(request, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable  Long id){
        projectService.deleteProjectById(id);
        return ResponseEntity.noContent().build();
    }
}
