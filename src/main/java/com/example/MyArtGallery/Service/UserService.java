package com.example.MyArtGallery.Service;

import com.example.MyArtGallery.Model.User;
import com.example.MyArtGallery.Repository.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserInterface userInterface;

    @Autowired
    public UserService(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public User signUp(User user) {
        // Validate if the username or email is already in use
        if (userInterface.findByUserName(user.getUserName()).isPresent()) {
            throw new RuntimeException("Username already in use");
        }

        if (userInterface.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }

        // Save the user to the database
        return userInterface.save(user);
    }

    public User login(User user){
        Optional<User> optionalUser = userInterface.findByUserNameOrEmail(user.getUserName(), user.getEmail());

        if (optionalUser.isPresent()){
            User usr = optionalUser.get();
            if (Objects.equals(usr.getPassword(), user.getPassword())){
                return usr;
            }
        }
        throw new RuntimeException();
    }
}