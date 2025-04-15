package org.project.easyf1.models.dto;

import org.project.easyf1.models.entity.User;

public class UserDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String userName;

    public UserDTO(String email, String firstName, String lastName, String username) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
    }

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUsername();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
