package org.example.freematchers.repository;

import org.example.freematchers.model.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long> {
    Optional<Developer> findByEmail(String email);

    @Query(value = """
    SELECT
        d.id AS id,
        d.name AS name,
        (d.workload - COALESCE(alloc.occupied_hours, 0)) AS freeHours,
        (COUNT(DISTINCT ds.skills) * 100.0 / :totalProjectSkills) AS matchPercentage
    FROM DEVELOPER d
    INNER JOIN DEVELOPER_SKILLS ds ON d.id = ds.developer_id
    LEFT JOIN (
        SELECT a.developer_id, SUM(p.required_hours) AS occupied_hours
        FROM ALLOCATION a
        INNER JOIN PROJECTS p ON a.project_id = p.id
        WHERE a.status_allocation = 'ACTIVE'
        GROUP BY a.developer_id
    ) alloc ON d.id = alloc.developer_id
    WHERE LOWER(ds.skills) IN (:projectSkills)
    GROUP BY d.id, d.name, d.workload, alloc.occupied_hours
    HAVING (d.workload - COALESCE(alloc.occupied_hours, 0)) >= :requiredHours
    ORDER BY matchPercentage DESC
    """, nativeQuery = true)
    List<DeveloperMatchProjection> findTopCandidates(
            @Param("requiredHours") Integer requiredHours,
            @Param("projectSkills") Set<String> projectSkills,
            @Param("totalProjectSkills") int totalProjectSkills
    );
}
