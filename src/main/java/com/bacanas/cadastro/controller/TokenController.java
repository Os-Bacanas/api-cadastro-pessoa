package com.bacanas.cadastro.controller;

import com.bacanas.cadastro.requests.LoginRequest;
import com.bacanas.cadastro.requests.LoginResponse;
import com.bacanas.cadastro.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Token", description = "Controller to generate token when logging in")
public class TokenController {
    private final UsersService usersService;

    public TokenController(UsersService usersService) {
        this.usersService = usersService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login user", description = "Method to login user")
    @ApiResponse(responseCode = "200", description = "User logged in successfully")
    @ApiResponse(responseCode = "400", description = "Email not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(usersService.login(loginRequest));
    }
}