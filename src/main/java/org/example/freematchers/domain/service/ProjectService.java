package org.example.freematchers.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.domain.port.in.ProjectUseCase;
import org.example.freematchers.domain.port.out.ProjectRepositoryPort;
import org.example.freematchers.domain.port.out.RecruiterRepositoryPort;
import org.example.freematchers.shared.exceptions.IdNotFoundException;
import org.example.freematchers.domain.model.Projects;
import org.example.freematchers.domain.model.Recruiter;
import java.util.List;

@RequiredArgsConstructor
public class ProjectService implements ProjectUseCase {

    private final ProjectRepositoryPort report;
    private final RecruiterRepositoryPort recruiterReport;

    @Override
    public Projects createProject(Projects project){
        var recruiter = exitsRecruiterId(project.getRecruiter().getId());
        project.assignRecruiter(recruiter);
        return report.save(project);
    }

    @Override
    public List<Projects> getProjectByRecruiterId(Long recruiterId){
       recruiterReport.findById(recruiterId);
        return report.findByRecruiterId(recruiterId);
    }

    @Override
    public Projects getProjectById(Long projectId){

        return exitsProjectId(projectId);
    }

    @Override
    public Projects updateProjectById(Projects projects, Long projectId){
        Projects project = exitsProjectId(projectId);

        return report.save(project);
    }

    @Override
    public void deleteProjectById(Long id){
        Projects project = exitsProjectId(id);

        if(project.getStatus()){
            throw new IdNotFoundException("ERROR! Active projects cannot be deleted. Deactivate it first.");
        }

        report.delete(project);
    }

    private Recruiter exitsRecruiterId(Long recruiterId){
        return recruiterReport.findById(recruiterId);
    }

    private Projects exitsProjectId(Long id){
        return report.findById(id);
    }
}
