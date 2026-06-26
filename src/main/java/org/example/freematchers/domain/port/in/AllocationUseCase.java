package org.example.freematchers.domain.port.in;

import org.example.freematchers.domain.model.Allocation;
import org.example.freematchers.shared.dto.request.AllocationRequest;
import org.example.freematchers.shared.dto.response.AllocationResponse;
import java.util.List;

public interface AllocationUseCase {
    Allocation bindDeveloperToProject(Allocation allocation);
    void projectCompletion(Long allocationId);
    List<Allocation> findMatchesForProjects(Long projectId);
}
