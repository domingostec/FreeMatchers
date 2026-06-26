package org.example.freematchers.domain.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Developer {

    private Long id;

    private String name;
    private String email;
    private String password;
    private Integer workload;
    private List<String> skills;

    public Developer(String name, String email, String password, Integer workload, List<String> skills) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.workload = workload;
        this.skills = skills;
    }

    public void changePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void changeEmail(String newEmail) {
        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = newEmail;
    }
}

