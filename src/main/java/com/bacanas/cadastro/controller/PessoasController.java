package com.bacanas.cadastro.controller;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.service.PessoasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoasController {
    private final PessoasService pessoasService;

    public PessoasController(PessoasService pessoasService) {
        this.pessoasService = pessoasService;
    }

    @GetMapping
    public ResponseEntity<List<PersonDTO>> listAll() {
        return ResponseEntity.ok(pessoasService.listAll());
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Person>> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(pessoasService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody PessoasPostRequestsBody pessoasPostRequestsBody, JwtAuthenticationToken token) {
        pessoasService.save(pessoasPostRequestsBody, token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/deletar-emails")
    public ResponseEntity<Void> delete(@RequestBody List<String> emails) {
        pessoasService.delete(emails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody PersonDTO personDTO) {
        pessoasService.replace(personDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}