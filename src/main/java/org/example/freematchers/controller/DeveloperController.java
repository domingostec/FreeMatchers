package org.example.freematchers.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.DeveloperRequest;
import org.example.freematchers.dto.response.DeveloperResponse;
import org.example.freematchers.service.DeveloperService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperController {

    private  final DeveloperService service;

    @PostMapping
    public ResponseEntity<DeveloperResponse> developerRegistration(@Valid @RequestBody DeveloperRequest request){

        return ResponseEntity.ok(service.registeringANewDeveloper(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponse> devInformation(@PathVariable Long id){
        return ResponseEntity.ok(service.getDevById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponse> updateDeveloperInformation(@Valid @RequestBody DeveloperRequest request,@PathVariable Long id){
        return ResponseEntity.ok(service.updateDeveloper(request, id));
    }

    @PutMapping("/{id}/skills")
    public ResponseEntity<DeveloperResponse> updateSkills(@RequestBody DeveloperRequest request, @PathVariable  Long id){
                return ResponseEntity.ok(service.updateSkillsDeveloper(request, id));
    }

}
