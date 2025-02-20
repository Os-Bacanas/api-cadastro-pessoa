package com.bacanas.cadastro.controller;


import com.bacanas.cadastro.domain.Pessoas;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import com.bacanas.cadastro.service.PessoasService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/pessoas")
public class PessoasController {
    private final PessoasService pessoasService;

    public PessoasController(PessoasService pessoasService) {
        this.pessoasService = pessoasService;
    }

    @GetMapping()
    public ResponseEntity<List<Pessoas>> lista() {

        return ResponseEntity.ok(pessoasService.listAll());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Pessoas> findById(@PathVariable long id) {

        return ResponseEntity.ok(pessoasService.findByIdOrThrowBadException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Pessoas>> findByName(@RequestParam(name = "name") String name) {
        //localhost:8080/pessoas/find?name

        return ResponseEntity.ok(pessoasService.findByName(name));
    }

    @PostMapping
    //ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Pessoas> save(@RequestBody PessoasPostRequestsBody pessoasPostRequestsBody) {
        return new ResponseEntity<>(pessoasService.save(pessoasPostRequestsBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        pessoasService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> replace(@RequestBody PessoasPutRequestsBody pessoasPutRequestsBody, @PathVariable long id) {
        pessoasService.replace(pessoasPutRequestsBody, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}