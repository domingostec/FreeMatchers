package org.example.freematchers.service;

import lombok.RequiredArgsConstructor;
import org.example.freematchers.dto.request.RecruiterRequest;
import org.example.freematchers.dto.response.RecruiterResponse;
import org.example.freematchers.exceptions.EmailAlreadyExistsException;
import org.example.freematchers.exceptions.IdNotFoundException;
import org.example.freematchers.mapper.RecruiterMapper;
import org.example.freematchers.model.Recruiter;
import org.example.freematchers.repository.RecruiterRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecruiterService {

    private final RecruiterRepository repository;
    private final RecruiterMapper recruiterMapper;

    public RecruiterResponse registeringRecruiter(RecruiterRequest request){
        existsByEmail(request.email());

        var recruiter  = recruiterMapper.recruiterResquestToRecruiter(request);

        repository.save(recruiter);

        return recruiterMapper.recruiterToRecruiterResponse(recruiter);
    }

    public RecruiterResponse getRecruiterById(Long id){
        Recruiter recruiter = repository.findById(id)
                .orElseThrow(() -> new IdNotFoundException("ID not found"));

        return  recruiterMapper.recruiterToRecruiterResponse(recruiter);
    }

   private void existsByEmail(String email){
        repository.findByEmail(email)
                .ifPresent(recruiter -> {throw new EmailAlreadyExistsException("Email Already Exists");});
   }
}
