package org.project.easyNascar.controllers.rest;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.project.framework.models.dto.LoginDTO;
import org.project.framework.models.dto.RegisterDTO;
import org.project.framework.models.dto.TokenDTO;
import org.project.framework.models.entity.User;
import org.project.framework.repositories.UserRepository;
import org.project.easyNascar.security.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final AuthenticationManager authentication;

    private final PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public AuthController(TokenService tokenService, UserRepository userRepository, AuthenticationManager authenticationManager, PasswordEncoder bCryptPasswordEncoder) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.authentication = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody LoginDTO loginDTO, HttpServletRequest request) {

        UsernamePasswordAuthenticationToken usernamePAT = new
                UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentic =
                authentication.authenticate(usernamePAT);

        String token = tokenService.TokenGenerator( (User) authentic.getPrincipal(), request);

        return ResponseEntity.ok(new TokenDTO(token));
    }

    @PostMapping("register")
    public ResponseEntity<TokenDTO> register(@Valid @RequestBody RegisterDTO registerDTO, HttpServletRequest request){

        User user = registerDTO.createUser();

        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        return ResponseEntity.ok(new TokenDTO(tokenService.TokenGenerator(this.userRepository.findByUsername(user.getUsername()), request)));
    }

}
