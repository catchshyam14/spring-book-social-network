package com.shyam.book.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
             @NonNull HttpServletRequest request,
             @NonNull HttpServletResponse response,
             @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        if (request.getServletPath().contains("/api/v1/auth")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        final String userEmail;
        System.out.println("authHeader: " + authHeader);
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
          filterChain.doFilter(request,response);
          return;
        }
        jwt = authHeader.substring(7);
        System.out.println("jwt: " + jwt);
        userEmail = jwtService.extractUsername(jwt);//extract userEmail from JWT token
        System.out.println("userEmail: " + userEmail);
        //if userEmail is not null then I want to check if the user is already
        // authenticated or not.
        // if getAuthentication() method return null means the user is not yet authenticated
        System.out.println("outside of if statement");
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("inside of if statement");
            //since user is not yet authenticated. we need to check if we have
            //the user within the database all right so to do so I will create an object
            //called user details
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
            System.out.println("User Details: " + userDetails);
            if(jwtService.isTokenValid(jwt, userDetails)){
                System.out.println("inside jwtService if statement");
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                System.out.println("authToken: " + authToken);
                //web authentication detail source
                // builds the details out
                //of our request out of our HTTP request
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
                System.out.println("SecurityContextHolder: " + SecurityContextHolder.getContext().getAuthentication());
            }

            filterChain.doFilter(request,response);
            System.out.println("After filter chain");

        }

    }
}
