package org.example.freematchers.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Recruiter {

    private Long id;

    private String name;

    private String email;

    private String password;

    private String enterprise;

    public void changePassword(String encryptedPassword) {
        this.password = encryptedPassword;
    }

    public void changeEmail(String newEmail) {
        if (newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("Email inválido");
        }
        this.email = newEmail;
    }


    public Recruiter(String name, String email, String password, String enterprise) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.enterprise = enterprise;
    }
}
