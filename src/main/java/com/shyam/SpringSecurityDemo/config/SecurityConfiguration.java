package com.shyam.SpringSecurityDemo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private  final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    //at the startup or at
    //the application startup Spring Security will try to look for for being of type
    //security filter chain and this security filter chain is the bean responsible of
    //configuring all the HTTP security of our application so I will create a bean

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CSRF is disabled because we are using token-based authentication
                .csrf(csrf -> csrf.disable())
                // Authorization configuration
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/v1/auth/**").permitAll() // Public endpoints
                                .anyRequest().authenticated() // Secured endpoints
                )
                // Session management: Stateless (for REST APIs)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // when we implemented the filter we want a once per request filter means
                //1:29:28
                //every request should be authenticated this means that we should not store the
                //1:29:34
                //authentication State or the session State should not be stored so the this
                //1:29:40
                //the session should be stateless and this will help us ensure that each request
                //1:29:46
                //should be authenticated

                // Configure authentication provider and filters
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);



        return http.build();

    }
}
