package org.project.easyf1.security.filter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.project.framework.repositories.UserRepository;
import org.project.easyf1.security.service.TokenService;
import org.project.easyf1.security.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;

@Component
public class SecurityTokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    private final Util util;


    @Autowired
    public SecurityTokenFilter(TokenService tokenService, UserRepository userRepository, Util util) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.util = util;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = takeToken(request);
        if (token != null){

            HashMap<String, String> resp = tokenService.getSubject(token);

            if(!resp.get("ip").equalsIgnoreCase(util.getClientIp(request))){
                throw new RuntimeException("Request IP does not match the IP used when the token was created.");
            }

            String subject = resp.get("username");

            UserDetails user = userRepository.findByUsername(subject);

            UsernamePasswordAuthenticationToken usernamePAT =
                    new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(usernamePAT);
        }

        filterChain.doFilter(request, response);
    }

    private String takeToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) return token.replace("Bearer ", "");
        return null;
    }
}
