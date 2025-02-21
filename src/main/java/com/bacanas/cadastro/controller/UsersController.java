package com.bacanas.cadastro.controller;


import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import com.bacanas.cadastro.service.UsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    private final UsersService usersService;

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> lista() {
        return ResponseEntity.ok(usersService.listAll());
    }

//    @GetMapping(path = "/{id}")
//    public ResponseEntity<User> findById(@PathVariable long id) {
//        return ResponseEntity.ok(usersService.findByIdOrThrowBadException(id));
//    }

    @GetMapping(path = "/")
    public ResponseEntity<List<User>> findByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(usersService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<User> save(@RequestBody UsersPostRequestsBody usersPostRequestsBody) {
        return new ResponseEntity<>(usersService.save(usersPostRequestsBody), HttpStatus.CREATED);
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