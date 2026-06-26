package org.example.freematchers.domain.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Allocation {

    private Long id;
    private String statusAllocation = "ACTIVE";
    private LocalDate allocationDate;
    private Developer developer;
    private Projects project;

    public void assignDeveloper(Developer developer) {
        if (developer == null) {
            throw new IllegalArgumentException("Developer não pode ser nulo");
        }
        this.developer = developer;
    }

    public void assignProject(Projects project) {
        if (project == null) {
            throw new IllegalArgumentException("Project não pode ser nulo");
        }
        this.project = project;
    }

    public void activate() {
        this.statusAllocation = "ACTIVE";
        this.allocationDate = LocalDate.now();
    }

    public void deactivate() {
        this.statusAllocation = "FINISHED";
    }

    public boolean isFinished() {
        return "FINISHED".equals(this.statusAllocation);
    }
}