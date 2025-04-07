package com.bacanas.cadastro.controller;

import com.bacanas.cadastro.config.SecurityConfig;
import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.requests.LoginResponse;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import com.bacanas.cadastro.service.UsersService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "User", description = "Controller for saving and editing user data")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    @Operation(summary = "Find user data", description = "Method to find user data")
    @ApiResponse(responseCode = "200", description = "User successfully found")
    @ApiResponse(responseCode = "400", description = "Email not registered")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<List<User>> lista() {
        return ResponseEntity.ok(usersService.listAll());
    }

    @PostMapping("/cadastro")
    @Operation(summary = "Save user data", description = "Method to save user data")
    @ApiResponse(responseCode = "201", description = "User successfully recorded")
    @ApiResponse(responseCode = "400", description = "Email already registered")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Void> save(@RequestBody UsersPostRequestsBody usersPostRequestsBody) {
        usersService.save(usersPostRequestsBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete user data", description = "Method to delete user data")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    @ApiResponse(responseCode = "400", description = "ID not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        usersService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Update user data", description = "Method to update user data")
    @ApiResponse(responseCode = "200", description = "User update successful")
    @ApiResponse(responseCode = "400", description = "Email not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<LoginResponse> replace(@RequestBody UsersPutRequestsBody usersPutRequestsBody) {
        LoginResponse response = usersService.replace(usersPutRequestsBody);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}