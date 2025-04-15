package org.project.easyf1.controllers.rest;

import org.project.easyf1.models.dto.UserDTO;
import org.project.easyf1.models.entity.User;
import org.project.easyf1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserDTO userDTO = new UserDTO(user);

        return ResponseEntity.ok(userDTO);
    }
}
