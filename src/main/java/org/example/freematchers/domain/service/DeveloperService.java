package org.example.freematchers.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.domain.model.Developer;
import org.example.freematchers.domain.port.in.DeveloperUseCase;
import org.example.freematchers.domain.port.out.DeveloperRepositoryPort;
import org.example.freematchers.shared.exceptions.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class DeveloperService implements DeveloperUseCase{

    private final DeveloperRepositoryPort report;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Developer registeringANewDeveloper(Developer developer){

        String encryptedPassword = passwordEncoder.encode(developer.getPassword());
        developer.changePassword(encryptedPassword);

        existsByEmail(developer.getEmail());

        return report.save(developer);
    }

    @Override
    public Developer getDevById(Long id){

        return report.findById(id);
    }

    @Override
    public Developer updateDeveloper(Developer developer, Long id) {

        var developerValid = report.findById(id);

        if (developerValid.getEmail() != null && !developerValid.getEmail().equals(developer.getEmail())) {
            existsByEmail(developer.getEmail());
            developer.changeEmail(developer.getEmail());
        }

       return report.save(developer);
    }

    @Override
    public Developer updateSkillsDeveloper(Developer developer, Long id){
        var developerValid = report.findById(id);


      return report.save(developer);

    }

    private void existsByEmail(String email){
        report.findByEmail(email)
                .ifPresent(d -> {throw new EmailAlreadyExistsException("Email Already Exists");});
    }
}