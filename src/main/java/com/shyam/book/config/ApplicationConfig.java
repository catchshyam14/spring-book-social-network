package com.shyam.book.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserDetailsService userDetailsService;

    @Bean
    public AuthenticationProvider authenticationProvider(){
        System.out.println("called authentication Provider  method: authenticationProvider()");
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return  authProvider;
    }

    //authentication manager as the name
    //1:36:54
    //indicates is the one responsible to manage the authentication so the authentication manager have or has a
    //1:37:03
    //bunch of methods and one of them where there is a method that allow us or help us to authenticate user based or using
    //1:37:11
    //just the username and password and for that we need also to create a bin or to
    //1:37:16
    //provide the bin to be able to use it later on

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        System.out.println("called authentication Manager  method: authenticationManager()");
        return  config.getAuthenticationManager();
    }

    //we want to authenticate a user we need to know which password encoded in order to be able to decode the password using
    //1:35:35
    //the correct algorithm
    @Bean
    public PasswordEncoder passwordEncoder(){
        System.out.println("called password Encoder  method: passwordEncoder()");
        return  new BCryptPasswordEncoder();
    }
}
