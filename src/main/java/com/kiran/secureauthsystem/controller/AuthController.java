package com.kiran.secureauthsystem.controller;

import com.kiran.secureauthsystem.dto.UserRegisterRequest;
import com.kiran.secureauthsystem.dto.UserRegisterResponse;
import com.kiran.secureauthsystem.service.HelloService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.kiran.secureauthsystem.dto.LoginRequest;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class AuthController {

    private final HelloService helloService;

    public AuthController(HelloService helloService) {
        this.helloService = helloService;
    }

    @PostMapping("/register")
    public UserRegisterResponse register(@RequestBody UserRegisterRequest request) {
        return helloService.register(request);
    }
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest request) {
        return helloService.login(request);
    }

    @GetMapping("/profile")
    public String getProfile() {
        return "This is a protected profile API 🔐";
    }



}
