package org.example.freematchers.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.domain.service.RecruiterService;
import org.example.freematchers.shared.dto.request.RecruiterRequest;
import org.example.freematchers.shared.dto.response.RecruiterResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiters")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService service;

    @PostMapping
    public ResponseEntity<RecruiterResponse> registerRecruiter(@Valid @RequestBody RecruiterRequest request){
        return ResponseEntity.status(201).body(service.registeringRecruiter(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruiterResponse> recruiterInfo(@PathVariable Long id){
        return ResponseEntity.ok(service.getRecruiterById(id));
    }
}
