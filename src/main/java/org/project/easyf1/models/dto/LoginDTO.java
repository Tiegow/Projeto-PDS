package org.project.easyf1.models.dto;


import jakarta.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public LoginDTO() {}

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
