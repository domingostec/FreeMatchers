package org.example.freematchers.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.application.mapper.AllocationMapper;
import org.example.freematchers.application.mapper.MatchMapper;
import org.example.freematchers.domain.model.Allocation;
import org.example.freematchers.domain.port.in.AllocationUseCase;
import org.example.freematchers.shared.dto.request.AllocationRequest;
import org.example.freematchers.shared.dto.response.AllocationResponse;
import org.example.freematchers.shared.dto.response.MatchResultResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/allocations")
@RequiredArgsConstructor
public class AllocationController {

    private final AllocationUseCase allocationUseCase;
    private final AllocationMapper allocationMapper;
    private final MatchMapper matchMapper;


    @GetMapping("/project/{projectId}/matches")
    public ResponseEntity<List<MatchResultResponse>> getProjectsMatching(@PathVariable Long projectId) {
        List<Allocation> allocations = allocationUseCase.findMatchesForProjects(projectId);

        List<MatchResultResponse> responses = allocations.stream()
                .map(allocation -> matchMapper.toMatchResponse(
                        allocation.getDeveloper(),
                        allocation.getProject().getProjectSkills()
                ))
                .toList();

        return ResponseEntity.ok(responses);
    }

    @PostMapping("/bind")
    public ResponseEntity<AllocationResponse> bindProject(@Valid @RequestBody AllocationRequest allocationRequest){
        return ResponseEntity.ok(allocationMapper.allocationToAllocationResponse(
                allocationUseCase.bindDeveloperToProject(
                        allocationMapper.allocationRequestToAllocation(allocationRequest)
                )
        ));
    }

    @DeleteMapping("/{allocationId}")
    public ResponseEntity<Void> unibindProject(@PathVariable Long allocationId){
        allocationUseCase.projectCompletion(allocationId);

        return ResponseEntity.noContent().build();
    }
}
