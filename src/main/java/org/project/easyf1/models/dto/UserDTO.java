package org.project.easyf1.models.dto;

import org.project.easyf1.models.entity.User;

public class UserDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String username;

    public UserDTO(String email, String firstName, String lastName, String username) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.username = user.getUsername();
    }

    @Override
    public String toString() {
        return "{" +
                "\"email\":\"" + email + "\"," +
                "\"firstName\":\"" + firstName + "\"," +
                "\"lastName\":\"" + lastName + "\"," +
                "\"username\":\"" + username + "\"" +
                "}";
    }    
}
