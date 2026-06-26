package org.example.freematchers.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Projects {

    private Long id;
    private String title;
    private String description;
    private List<String> projectSkills;
    private Integer requiredHours = 20;
    private Boolean status = true;
    private Recruiter recruiter;

    public void assignRecruiter(Recruiter recruiter) {
        if(recruiter == null){
            throw new RuntimeException("Recruiter cannot be null");
        }

        this.recruiter = recruiter;
    }
}
