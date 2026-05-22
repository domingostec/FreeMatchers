package org.example.freematchers.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.DeveloperRequest;
import org.example.freematchers.dto.response.DeveloperResponse;
import org.example.freematchers.exceptions.EmailAlreadyExistsException;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.model.Developer;
import org.example.freematchers.repository.DeveloperRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;

    private DeveloperResponse toResponse(Developer developer){
        return new DeveloperResponse(
                developer.getName(),
                developer.getEmail(),
                developer.getWorkload(),
                developer.getSkills()
        );
    }

    public DeveloperResponse registeringANewDeveloper(DeveloperRequest request){

        existsByEmail(request.email());

        String encryptedPassword = passwordEncoder.encode(request.password());

        Developer developer = new Developer(
                request.name(),
                request.email(),
                encryptedPassword,
                request.workload(),
                request.skills()
        );

        developerRepository.save(developer);
        return toResponse(developer);

    }

    public DeveloperResponse getDevById(Long id){
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not found"));

        return toResponse(developer);
    }

    public DeveloperResponse updateDeveloper(DeveloperRequest request, Long id) {

        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not Found"));

        if (request.email() != null && !request.email().equals(developer.getEmail())) {
            existsByEmail(request.email());
            developer.setEmail(request.email());
        }

       developer.updateFromDTO(request);

        return  toResponse(developerRepository.save(developer));
    }

    public DeveloperResponse updateSkillsDeveloper(DeveloperRequest request, Long id){
        Developer developer = developerRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not Found"));

      List<String> nowSkills = developer.getSkills();

      if(request.skills() != null){
          nowSkills.addAll(request.skills());
      }

      developer.setSkills(nowSkills);

        return toResponse(developerRepository.save(developer));
    }

    private void existsByEmail(String email){
        developerRepository.findByEmail(email)
                .ifPresent(d -> {throw new EmailAlreadyExistsException("Email Already Exists");});
    }
}
