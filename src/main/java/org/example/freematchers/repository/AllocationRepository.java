package org.example.freematchers.repository;

import org.example.freematchers.model.Allocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AllocationRepository extends JpaRepository<Allocation, Long> {
    List<Allocation> findByDeveloperIdAndStatusAllocation(Long developerId, String statusAllocation);


    @Query("SELECT COALESCE(SUM(p.requiredHours), 0) FROM Allocation a JOIN a.project p WHERE a.developer.id = :developerId AND a.statusAllocation = 'ACTIVE'")
    Integer sumOccupiedHoursByDeveloperId(@Param("developerId") Long developerId);

}
