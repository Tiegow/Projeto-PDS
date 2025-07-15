package org.project.easyNascar.security.configuration;


import org.project.easyNascar.security.filter.SecurityTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig{

    private final SecurityTokenFilter securityTokenFilter;

    @Autowired
    public SecurityConfig(SecurityTokenFilter securityTokenFilter) {
        this.securityTokenFilter = securityTokenFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.ignoringRequestMatchers("/**"));

        httpSecurity.authorizeHttpRequests(authorizeRequests ->
                authorizeRequests.requestMatchers("/components/**", "/css/**", "/js/**", "/images/**").permitAll());

        httpSecurity.formLogin(formLogin -> formLogin.loginPage("/easyF1/auth"));

        httpSecurity.authorizeHttpRequests((authorization) ->
                        // Permissões de API
                authorization.requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/liveSession/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/meetings/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/sessions/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/driver/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/llm/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/live/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/teams/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/user/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/api/user/**").hasRole("USER")
                        // permissões de views
                        .requestMatchers(HttpMethod.GET, "/easyF1/**").permitAll()
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
