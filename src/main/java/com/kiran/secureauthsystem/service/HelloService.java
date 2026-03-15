package com.kiran.secureauthsystem.service;
import com.kiran.secureauthsystem.dto.LoginRequest;

import com.kiran.secureauthsystem.dto.UserRegisterRequest;
import com.kiran.secureauthsystem.dto.UserRegisterResponse;
import com.kiran.secureauthsystem.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.kiran.secureauthsystem.repository.UserRepository;
import com.kiran.secureauthsystem.security.JwtUtil;


@Service
public class HelloService {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public HelloService(BCryptPasswordEncoder passwordEncoder,
                        UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    public UserRegisterResponse register(UserRegisterRequest request) {

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        user.setCreatedAt("now");
        User savedUser = userRepository.save(user);

        UserRegisterResponse response = new UserRegisterResponse();
        response.setId(savedUser.getId());
        response.setUsername(savedUser.getUsername());
        response.setEmail(savedUser.getEmail());


        return response;
    }
    public String login(LoginRequest request) {

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        boolean passwordMatch = passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        );

        if (!passwordMatch) {
            throw new RuntimeException("Invalid credentials");
        }

        // Generate JWT
        String token = JwtUtil.generateToken(
                user.getUsername(),
                user.getRole()
        );


        return token;
    }



}
