package org.example.freematchers.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.ProjectRequest;
import org.example.freematchers.dto.response.ProjectResponse;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.mapper.ProjectMapper;
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
    private final ProjectMapper projectMapper;

    public ProjectResponse createProject(ProjectRequest request){

        var recruiter = exitsRecruiterId(request);
        var project = projectMapper.projectRequestToProject(request);

        project.setRecruiter(recruiter);

        projectRepository.save(project);
        return projectMapper.projectToProjectResponse(project);
    }

    public List<ProjectResponse> getProjectByRecruiterId(Long recruiterId){
        Recruiter recruiter = recruiterRepository.findById(recruiterId)
                .orElseThrow(() -> new IdNotFoundException("Recruiter not found"));

        List<Projects> projectsList = projectRepository.findByRecruiterId(recruiterId);

        return projectMapper.toResponseList(projectsList);
    }

    public ProjectResponse getProjectById(Long projectId){
        Projects project = exitsProjectId(projectId);

        return projectMapper.projectToProjectResponse(project);
    }

    public ProjectResponse updateProjectById(ProjectRequest request, Long projectId){
        Projects project = exitsProjectId(projectId);

        projectMapper.updateProjectFromRequest(request, project);

        projectRepository.save(project);

        return projectMapper.projectToProjectResponse(project);
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
