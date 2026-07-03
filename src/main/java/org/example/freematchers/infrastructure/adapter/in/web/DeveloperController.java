package org.example.freematchers.infrastructure.adapter.in.web;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.freematchers.application.mapper.DeveloperMapper;
import org.example.freematchers.domain.port.in.DeveloperUseCase;
import org.example.freematchers.shared.dto.request.DeveloperRequest;
import org.example.freematchers.shared.dto.response.DeveloperResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/developers")
@RequiredArgsConstructor
public class DeveloperController {

    private final DeveloperMapper developerMapper;
    private  final DeveloperUseCase developerUseCase;

    @PostMapping
    public ResponseEntity<DeveloperResponse> developerRegistration(@Valid @RequestBody DeveloperRequest request){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(developerMapper.developerToDeveloperResponse(
                        developerUseCase.registeringANewDeveloper(
                                developerMapper.developerRequestToDeveloper(request)
                        )
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeveloperResponse> devInformation(@PathVariable Long id){
        return ResponseEntity.ok(developerMapper.developerToDeveloperResponse(
                developerUseCase.getDevById(id)
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeveloperResponse> updateDeveloperInformation(@Valid @RequestBody DeveloperRequest request,@PathVariable Long id){
        return ResponseEntity.ok(developerMapper.developerToDeveloperResponse(
                developerUseCase.updateDeveloper(
                        developerMapper.developerRequestToDeveloper(request), id)
        ));
    }

    @PutMapping("/{id}/skills")
    public ResponseEntity<DeveloperResponse> updateSkills(@Valid @RequestBody DeveloperRequest request, @PathVariable  Long id){
        return ResponseEntity.ok(developerMapper.developerToDeveloperResponse(
                developerUseCase.updateDeveloper(
                        developerMapper.developerRequestToDeveloper(request), id)
        ));
    }

}
