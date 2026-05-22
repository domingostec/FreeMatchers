package org.example.freematchers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.freematchers.dto.request.ProjectRequest;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "project_skills", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "skill")
    private List<String> projectSkills;

    private Integer requiredHours = 20;

    private Boolean status = true;

    @ManyToOne
    private Recruiter recruiter;


    public void updateFromDTO(ProjectRequest dto) {
        this.title = dto.title() != null ? dto.title() : this.title;
        this.description = dto.description() != null ? dto.description() : this.description;
        this.requiredHours = dto.requiredHours() != null ? dto.requiredHours() : this.requiredHours;
    }

}
