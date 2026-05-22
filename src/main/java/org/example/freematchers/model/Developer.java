package org.example.freematchers.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.freematchers.dto.request.DeveloperRequest;

import java.util.List;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Developer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String senha;
    private Integer workload;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "developer_skills", joinColumns = @JoinColumn(name = "developer_id"))
    @Column(name = "skills")
    private List<String> skills;

    public Developer(String name, String email, String senha, Integer workload, List<String> skills) {
        this.name = name;
        this.email = email;
        this.senha = senha;
        this.workload = workload;
        this.skills = skills;
    }

    public void updateFromDTO(DeveloperRequest dto) {
        this.name = dto.name() != null ? dto.name() : this.name;
        this.email = dto.email() != null ? dto.email() : this.email;
        this.workload = dto.workload() != null ? dto.workload() : this.workload;
    }

}
