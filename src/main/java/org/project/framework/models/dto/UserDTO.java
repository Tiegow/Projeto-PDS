package org.project.framework.models.dto;

import java.util.Set;
import java.util.stream.Collectors;
import org.project.framework.models.entity.User;

public class UserDTO {

    private String email;
    private String firstName;
    private String lastName;
    private String userName;
    private Set<DriverDTO> favoriteDrivers;
    private Set<TeamDTO> favoriteTeams;

    public UserDTO(String email, String firstName, String lastName, String username, Set<DriverDTO> favoriteDrivers, Set<TeamDTO> favoriteTeams) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = username;
        this.favoriteDrivers = favoriteDrivers;
        this.favoriteTeams = favoriteTeams;
    }

    public UserDTO(User user) {
        this.email = user.getEmail();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.userName = user.getUsername();
        this.favoriteDrivers = user.getFavoriteDrivers().stream() // Converte cada entidade Driver para DriverDTO e coloca em um Set
            .map(DriverDTO::new)
            .collect(Collectors.toSet());
        this.favoriteTeams = user.getFavoriteTeams().stream() 
            .map(TeamDTO::new)
            .collect(Collectors.toSet());
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

    public Set<DriverDTO> getFavoriteDrivers() {
        return favoriteDrivers;
    }

    public void setFavoriteDrivers(Set<DriverDTO> favoriteDrivers) {
        this.favoriteDrivers = favoriteDrivers;
    }

    public Set<TeamDTO> getFavoriteTeams() {
        return favoriteTeams;
    }

    public void setFavoriteTeams(Set<TeamDTO> favoriteTeams) {
        this.favoriteTeams = favoriteTeams;
    }
}
