package org.example.freematchers.domain.port.in;

import org.example.freematchers.domain.model.Projects;

import java.util.List;

public interface ProjectUseCase {
    Projects createProject(Projects projects);
    List<Projects> getProjectByRecruiterId(Long id);
    Projects getProjectById(Long id);
    Projects updateProjectById(Projects projects, Long id);
    void deleteProjectById(Long id);
}
