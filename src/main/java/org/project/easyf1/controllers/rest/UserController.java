package org.project.easyf1.controllers.rest;

import org.project.easyf1.models.dto.UserDTO;
import org.project.easyf1.models.entity.User;
import org.project.easyf1.services.UserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("user")
public class UserController {

    private final UserDetailServiceImpl userDetailService;

    @Autowired
    public UserController(UserDetailServiceImpl userDetailService) {
        this.userDetailService = userDetailService;
    }

    @GetMapping("/get")
    public ResponseEntity<String> getUser(@RequestParam String username) {
        User user = (User) userDetailService.loadUserByUsername(username);

        UserDTO userDTO = new UserDTO(user);

        return ResponseEntity.ok(userDTO.toString());
    }
}
