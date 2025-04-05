package org.project.easyf1.models.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.project.easyf1.models.entity.Role;
import org.project.easyf1.models.entity.User;

import java.util.GregorianCalendar;
import java.util.HashSet;

@Data
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

    @NotBlank
    private GregorianCalendar birthday;


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

}
