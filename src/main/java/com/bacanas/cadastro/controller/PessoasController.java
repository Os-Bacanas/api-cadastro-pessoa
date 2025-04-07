package com.bacanas.cadastro.controller;

import com.bacanas.cadastro.config.SecurityConfig;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.service.PessoasService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
@Tag(name = "People", description = "Controller for saving and editing person data")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class PessoasController {
    private final PessoasService pessoasService;

    public PessoasController(PessoasService pessoasService) {
        this.pessoasService = pessoasService;
    }

    @GetMapping
    @Operation(summary = "Find person data", description = "Method to find person data")
    @ApiResponse(responseCode = "200", description = "Person successfully found")
    @ApiResponse(responseCode = "400", description = "Email not registered")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<List<PersonDTO>> listAll(JwtAuthenticationToken token) {
        return ResponseEntity.ok(pessoasService.listByUser(token));
    }

    @PostMapping
    @Operation(summary = "Save person data", description = "Method to save person data")
    @ApiResponse(responseCode = "201", description = "Person successfully recorded")
    @ApiResponse(responseCode = "400", description = "Email already registered")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Void> save(@RequestBody PersonDTO personDTO, JwtAuthenticationToken token) {
        pessoasService.save(personDTO, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar-emails")
    @Operation(summary = "Delete person data", description = "Method to delete person data")
    @ApiResponse(responseCode = "204", description = "Person deleted successfully")
    @ApiResponse(responseCode = "400", description = "Email not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Void> delete(@RequestBody List<String> emails) {
        pessoasService.delete(emails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    @Operation(summary = "Update person data", description = "Method to update person data")
    @ApiResponse(responseCode = "204", description = "Person update successful")
    @ApiResponse(responseCode = "400", description = "Email not found")
    @ApiResponse(responseCode = "500", description = "Server error")
    public ResponseEntity<Void> replace(@RequestBody PersonDTO personDTO) {
        pessoasService.replace(personDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}