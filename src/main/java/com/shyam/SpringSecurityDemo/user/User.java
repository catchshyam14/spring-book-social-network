package com.shyam.SpringSecurityDemo.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user" )
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    private String firstname;

    private String lastname;

    @Column(
            unique = true
    )
    private String email;

    private String password;

    //@Enumerated(EnumType.STRING) annotation ensures that the role is stored in the database as a string (e.g., "ADMIN") rather than its ordinal value (0, 1, etc.).
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //The name() method of an Enum returns the exact name of the Enum constant as a String.
        //For example:
        //Role role = Role.ADMIN;
        //System.out.println(role.name()); // Outputs: "ADMIN"
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    //Don't forget to override this method which is a part of UserDetails interface
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; //email is our username
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
