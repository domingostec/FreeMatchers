package org.example.freematchers.repository;

import org.example.freematchers.model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Projects, Long> {
    List<Projects> findByRecruiterId(Long recruiterId);
}
