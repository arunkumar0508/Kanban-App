package com.example.UserLogin.controller;

import com.example.UserLogin.domain.User;
import com.example.UserLogin.exception.InvalidCustomerDetails;
import com.example.UserLogin.exception.UserAlreadyExist;
import com.example.UserLogin.security.SecurityTokenGenerator;
import com.example.UserLogin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/api/users")



public class UserController {
    private UserService userService;
    private SecurityTokenGenerator securityTokenGenerator;


    @Autowired
    public UserController(UserService userService, SecurityTokenGenerator securityTokenGenerator) {
        this.securityTokenGenerator=securityTokenGenerator;
                this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            User savedUser = userService.registerUser(user);
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (UserAlreadyExist e) {
            return new ResponseEntity<>("User with this email already exists", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loggedInUser = userService.loginUser(user.getEmail(), user.getPassword());
            Map<String, String> tokenMap = securityTokenGenerator.generateToken(loggedInUser);
            return new ResponseEntity<>(tokenMap, HttpStatus.OK);
        } catch (InvalidCustomerDetails e) {
            return new ResponseEntity<>("Invalid email or password", HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String token = extractTokenFromRequest(request);
        token = null;
        return ResponseEntity.ok("Logout successful");
    }

    public String extractTokenFromRequest(HttpServletRequest request) {
        String tokenHeader = request.getHeader("Authorization");

        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            return tokenHeader.substring(7);
        }

        return null;
    }

}
