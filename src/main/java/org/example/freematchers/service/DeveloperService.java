package org.example.freematchers.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.DeveloperRequest;
import org.example.freematchers.dto.response.DeveloperResponse;
import org.example.freematchers.exceptions.EmailAlreadyExistsException;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.mapper.DeveloperMapper;
import org.example.freematchers.repository.DeveloperRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeveloperService {

    private final DeveloperRepository developerRepository;
    private final PasswordEncoder passwordEncoder;
    private final DeveloperMapper developerMapper;

    public DeveloperResponse registeringANewDeveloper(DeveloperRequest request){

        existsByEmail(request.email());

        var developer = developerMapper.developerRequestToDeveloper(request);

        String encryptedPassword = passwordEncoder.encode(request.password());
        developer.setPassword(encryptedPassword);

        developerRepository.save(developer);
        return developerMapper.developerToDeveloperResponse(developer);
    }

    public DeveloperResponse getDevById(Long id){
        var developer = developerRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not found"));

        return developerMapper.developerToDeveloperResponse(developer);
    }

    public DeveloperResponse updateDeveloper(DeveloperRequest request, Long id) {

        var developer = developerRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not Found"));

        if (request.email() != null && !request.email().equals(developer.getEmail())) {
            existsByEmail(request.email());
            developer.setEmail(request.email());
        }

      developerMapper.updateDeveloperFromRequest(request, developer);

       var saveDeveloper = developerRepository.save(developer);
       return  developerMapper.developerToDeveloperResponse(saveDeveloper);
    }

    public DeveloperResponse updateSkillsDeveloper(DeveloperRequest request, Long id){
        var developer = developerRepository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not Found"));

      developerMapper.updateSkillsFromRequest(request, developer);

      var saveDeveloper = developerRepository.save(developer);

      return developerMapper.developerToDeveloperResponse(saveDeveloper);
    }

    private void existsByEmail(String email){
        developerRepository.findByEmail(email)
                .ifPresent(d -> {throw new EmailAlreadyExistsException("Email Already Exists");});
    }
}