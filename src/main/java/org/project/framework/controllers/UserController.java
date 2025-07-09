package org.project.framework.controllers;

import org.project.easyf1.models.dto.UserDTO;
import org.project.easyf1.models.entity.User;
import org.project.framework.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("get")
    public ResponseEntity<UserDTO> getUser() {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserDTO userDTO = userService.getUser(authUser.getId());
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("favorite-driver/{driverNumber}")
    public ResponseEntity<Void> addFavoriteDriver(@PathVariable Integer driverNumber) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.addFavoriteDriver(authUser.getUsername(), driverNumber);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("favorite-driver/{driverNumber}")
    public ResponseEntity<Void> removeFavoriteDriver(@PathVariable Integer driverNumber) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.removeFavoriteDriver(authUser.getUsername(), driverNumber);
        return ResponseEntity.ok().build();
    }

    @PostMapping("favorite-team/{teamId}")
    public ResponseEntity<Void> addFavoriteTeam(@PathVariable Long teamId) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.addFavoriteTeam(authUser.getUsername(), teamId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("favorite-team/{teamId}")
    public ResponseEntity<Void> removeFavoriteTeam(@PathVariable Long teamId) {
        User authUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        userService.removeFavoriteTeam(authUser.getUsername(), teamId);
        return ResponseEntity.ok().build();
    }
}
