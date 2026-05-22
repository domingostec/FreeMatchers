package org.example.freematchers.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.RecruiterRequest;
import org.example.freematchers.dto.response.RecruiterResponse;
import org.example.freematchers.service.RecruiterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiters")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService service;

    @PostMapping
    public ResponseEntity<RecruiterResponse> registerRecruiter(@Valid @RequestBody RecruiterRequest request){
        return ResponseEntity.ok(service.registeringRecruiter(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruiterResponse> recruiterInfo(@PathVariable Long id){
        return ResponseEntity.ok(service.getRecruiterById(id));
    }
}
