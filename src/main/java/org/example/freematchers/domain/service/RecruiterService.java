package org.example.freematchers.domain.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.domain.port.in.RecruiterUseCase;
import org.example.freematchers.domain.port.out.RecruiterRepositoryPort;
import org.example.freematchers.shared.exceptions.EmailAlreadyExistsException;
import org.example.freematchers.domain.model.Recruiter;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class RecruiterService implements RecruiterUseCase {

    private final RecruiterRepositoryPort report;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Recruiter registeringRecruiter(Recruiter recruiter){
        existsByEmail(recruiter.getEmail());

        String encryptedPassword = passwordEncoder.encode(recruiter.getPassword());
        recruiter.changePassword(encryptedPassword);

        return report.save(recruiter);

    }

    @Override
    public Recruiter getRecruiterById(Long id){

        return report.findById(id);
    }

   private void existsByEmail(String email){
        report.findByEmail(email)
                .ifPresent(recruiter -> {throw new EmailAlreadyExistsException("Email Already Exists");});
   }
}
