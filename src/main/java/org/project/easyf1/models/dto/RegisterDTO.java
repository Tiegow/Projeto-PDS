package org.project.easyf1.models.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.project.easyf1.models.entity.Role;
import org.project.easyf1.models.entity.User;

import java.util.GregorianCalendar;
import java.util.HashSet;

public class RegisterDTO {

    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String username;

    @NotNull
    private GregorianCalendar birthday;

    public RegisterDTO() {
    }

    public User createUser(){
        User user = new User();

        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setBirthday(birthday);
        user.setRoles(new HashSet<>());
        user.getRoles().add(Role.ROLE_USER);

        return user;
    }

    public LoginDTO createLoginDTO(){
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);
        return loginDTO;
    }


    public @NotBlank String getEmail() {
        return email;
    }

    public void setEmail(@NotBlank String email) {
        this.email = email;
    }

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }

    public @NotBlank String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotBlank String firstName) {
        this.firstName = firstName;
    }

    public @NotBlank String getLastName() {
        return lastName;
    }

    public void setLastName(@NotBlank String lastName) {
        this.lastName = lastName;
    }

    public @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotBlank String username) {
        this.username = username;
    }

    public @NotNull GregorianCalendar getBirthday() {
        return birthday;
    }

    public void setBirthday(@NotNull GregorianCalendar birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", birthday=" + birthday +
                '}';
    }
}
