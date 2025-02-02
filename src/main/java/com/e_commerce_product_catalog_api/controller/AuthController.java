package com.e_commerce_product_catalog_api.controller;

import com.e_commerce_product_catalog_api.entity.User;
import com.e_commerce_product_catalog_api.jwt.JwtTokenUtil;
import com.e_commerce_product_catalog_api.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtTokenUtil jwtTokenUtil, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Operation(
            summary = "User Registration",
            description = "User will be registered",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User registered successfully!"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/register")
    public ResponseEntity<String> register(@Parameter(description = "User details that need to be created", required = true)
                                               @Valid @RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<String>("User registered successfully!", HttpStatus.OK);
    }

    @Operation(
            summary = "User login",
            description = "User login will generate a JWT token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token will be generated successfully"),
                    @ApiResponse(responseCode = "500", description = "Internal Server Error")
            }
    )
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Parameter(description = "A map contains the required user details to login", required = true)
                                                         @RequestBody Map<String, String> request) {
        User user = userRepository.findByUsername(request.get("username"))
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(passwordEncoder.matches(request.get("password"), user.getPassword())) {
            String token = jwtTokenUtil.generateToken(user.getUsername());
            return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
        }
        else {
            throw new RuntimeException("Invalid Credentials");
        }
    }
}
