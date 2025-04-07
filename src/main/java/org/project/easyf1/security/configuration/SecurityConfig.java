package org.project.easyf1.security.configuration;


import org.project.easyf1.security.filter.SecurityTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final SecurityTokenFilter securityTokenFilter;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    public SecurityConfig(SecurityTokenFilter securityTokenFilter, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.securityTokenFilter = securityTokenFilter;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/**"));

        httpSecurity.authorizeHttpRequests((authorization) ->
                authorization.requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.GET, "/home").permitAll()
                        .requestMatchers(HttpMethod.GET, "/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/user/**").hasRole("USER")
                        .anyRequest().authenticated());

        httpSecurity.addFilterBefore(securityTokenFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity.sessionManagement((session) ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return  httpSecurity.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http, AuthenticationProvider authenticationProvider) throws Exception {

        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);

        authenticationManagerBuilder.authenticationProvider(authenticationProvider);

        return authenticationManagerBuilder.build();
    }

}
