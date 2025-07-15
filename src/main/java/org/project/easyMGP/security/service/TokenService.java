package org.project.easyMGP.security.service;


import jakarta.servlet.http.HttpServletRequest;
import org.project.framework.models.entity.User;
import org.project.easyMGP.security.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private Util util;

    public String TokenGenerator(User user, HttpServletRequest request) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("easyf1")
                    .withSubject(user.getUsername() + "$$^^??" + util.getClientIp(request))
                    .withExpiresAt(time())
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Token Error");
        }
    }

    public HashMap<String, String> getSubject(String token) {

        HashMap<String, String> resp = new HashMap<>();

        try {

            Algorithm algorithm = Algorithm.HMAC256(secret);

            String subject = JWT.require(algorithm)
                    .withIssuer("easyf1")
                    .build()
                    .verify(token)
                    .getSubject();

            String[] split = subject.split("\\$\\$\\^\\^\\?\\?");
            resp.put("username", split[0]);
            resp.put("ip", split[1]);

            return resp;

        } catch (JWTVerificationException exception){
            throw new RuntimeException(exception.getCause());
        }

    }

    private Instant time() {
        return LocalDateTime.now().plusHours(4).toInstant(ZoneOffset.UTC);
    }
}
