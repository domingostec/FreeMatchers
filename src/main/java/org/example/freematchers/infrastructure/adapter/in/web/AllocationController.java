package org.example.freematchers.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.domain.service.AllocationService;
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

    private final AllocationService allocationService;


    @GetMapping("/project/{projectId}/matches")
    public ResponseEntity<List<MatchResultResponse>> getProjectsMatching(@PathVariable Long projectId){
        return ResponseEntity.ok(allocationService.findMatchesForProjects(projectId));
    }

    @PostMapping("/bind")
    public ResponseEntity<AllocationResponse> bindProject(@Valid @RequestBody AllocationRequest allocationRequest){
        return ResponseEntity.ok(allocationService.bindDeveloperToProject(allocationRequest));
    }

    @DeleteMapping("/{allocationId}")
    public ResponseEntity<Void> unibindProject(@PathVariable Long allocationId){
        allocationService.projectCompletion(allocationId);

        return ResponseEntity.noContent().build();
    }
}
