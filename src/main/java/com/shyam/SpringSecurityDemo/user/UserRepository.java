package com.shyam.SpringSecurityDemo.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    //We are finding users by email. Since email is unique
    // If no user with the specified email exists,this method will throw an exception
    // if generic class Optional is not included. Since Optinal is included
    //If no user with the specified email exists, the Optional will be empty.
    // and it forces the caller to handle the absence explicitly. Instead of throwing an exception

    //Usage: This method (Optional<User>)is preferred when a user might not exist, as it forces the caller to handle the absence explicitly. Instead of throwing an exception, you can use the Optional methods to deal with the case where no user is found.

    public Optional<User> findByEmail(String email);

}
