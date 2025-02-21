package com.bacanas.cadastro.controller;

import com.bacanas.cadastro.requests.LoginRequest;
import com.bacanas.cadastro.requests.LoginResponse;
import com.bacanas.cadastro.service.UsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {
    private final UsersService usersService;

    public TokenController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(usersService.login(loginRequest));
    }
}