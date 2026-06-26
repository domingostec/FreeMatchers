package org.example.freematchers.domain.port.in;

import org.example.freematchers.domain.model.Recruiter;

public interface RecruiterUseCase {
    Recruiter registeringRecruiter(Recruiter recruiter);
    Recruiter getRecruiterById(Long id);

}
