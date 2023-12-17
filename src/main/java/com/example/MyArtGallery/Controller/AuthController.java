package com.example.MyArtGallery.Controller;

import com.example.MyArtGallery.Model.User;
import com.example.MyArtGallery.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = "/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User userRequest) {
        try {
            User newUser = userService.signUp(userRequest);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User userRequest) {
        try {
            User newUser = userService.login(userRequest);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid username/email or password");
        }
    }
}
