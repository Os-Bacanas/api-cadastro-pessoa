package com.bacanas.cadastro.controller;

import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import com.bacanas.cadastro.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;
    private final BCryptPasswordEncoder passwordEncoder;

    public UsersController(UsersService usersService, BCryptPasswordEncoder passwordEncoder) {
        this.usersService = usersService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public ResponseEntity<List<User>> lista() {
        return ResponseEntity.ok(usersService.listAll());
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity<User> findById(@PathVariable String email) {
        return ResponseEntity.ok(usersService.findByEmail(email));
    }

    @GetMapping(path = "/")
    public ResponseEntity<List<User>> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(usersService.findByName(name));
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Void> save(@RequestBody UsersPostRequestsBody usersPostRequestsBody) {
        usersService.save(usersPostRequestsBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        usersService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody UsersPutRequestsBody usersPutRequestsBody) {
        usersService.replace(usersPutRequestsBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}