package org.example.freematchers.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.ProjectRequest;
import org.example.freematchers.dto.response.ProjectResponse;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.model.Projects;
import org.example.freematchers.model.Recruiter;
import org.example.freematchers.repository.ProjectRepository;
import org.example.freematchers.repository.RecruiterRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final RecruiterRepository recruiterRepository;

    private final ProjectRepository projectRepository;

    private ProjectResponse toResponse(Projects projects) {
        return new ProjectResponse(
                projects.getId(),
                projects.getTitle(),
                projects.getDescription(),
                projects.getRequiredHours(),
                projects.getProjectSkills(),
                projects.getStatus(),
                projects.getRecruiter().getId(),
                projects.getRecruiter().getName()
        );
    }


    public ProjectResponse createProject(ProjectRequest request){
        Recruiter recruiter = exitsRecruiterId(request);

        Projects project = new Projects();
        project.setTitle(request.title());
        project.setDescription(request.description());
        project.setRequiredHours(request.requiredHours());
        project.setProjectSkills(request.projectSkills());

        project.setRecruiter(recruiter);

        return toResponse(projectRepository.save(project));
    }

    public List<ProjectResponse> getProjectByRecruiterId(Long recruiterId){
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new IdNotFoundException("Recruiter not found"));

        List<Projects> projectsList = projectRepository.findByRecruiterId(recruiterId);

        return projectsList.stream()
                .map(this::toResponse)
                .toList();
    }

    public ProjectResponse getProjectById(Long projectId){
        Projects project = exitsProjectId(projectId);
        return toResponse(project);
    }

    public ProjectResponse updateProjectById(ProjectRequest request, Long projectId){
        Projects project = exitsProjectId(projectId);

        project.updateFromDTO(request);

        List<String> nowSkills = project.getProjectSkills();

        if(request.projectSkills() != null){
            nowSkills.addAll(request.projectSkills());
        }

        project.setProjectSkills(nowSkills);

        return toResponse(projectRepository.save(project));
    }

    public void deleteProjectById(Long id){
        Projects project = exitsProjectId(id);

        if(project.getStatus()){
            throw new IdNotFoundException("ERROR! Active projects cannot be deleted. Deactivate it first.");
        }

        projectRepository.delete(project);
    }


    private Recruiter exitsRecruiterId(ProjectRequest request){
        return recruiterRepository.findById(request.recruiterId())
                .orElseThrow(() -> new IdNotFoundException("ERROR! ID Not found"));
    }

    private Projects exitsProjectId(Long id){
        return projectRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("Project ID not found"));
    }
}
