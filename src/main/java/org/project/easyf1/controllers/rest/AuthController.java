package org.project.easyf1.controllers.rest;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.project.easyf1.models.dto.LoginDTO;
import org.project.easyf1.models.dto.RegisterDTO;
import org.project.easyf1.models.dto.TokenDTO;
import org.project.easyf1.models.entity.User;
import org.project.easyf1.repositories.UserRepository;
import org.project.easyf1.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final AuthenticationManager authentication;

    @Autowired
    public AuthController(TokenService tokenService, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authentication = authenticationManager;
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken usernamePAT = new
                UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentic =
                authentication.authenticate(usernamePAT);

        String token = tokenService.TokenGenerator(this.userRepository.findByUsername(loginDTO.getUsername()), request);

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("register")
    public ResponseEntity<TokenDTO> register(@Valid @RequestBody RegisterDTO registerDTO, HttpServletRequest request){

        User user = registerDTO.createUser();

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        userRepository.save(user);

        return login(registerDTO.createLoginDTO(), request);
    }

}
