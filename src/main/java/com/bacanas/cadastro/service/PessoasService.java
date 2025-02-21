package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.mapper.PessoasMapper;
import com.bacanas.cadastro.repository.PessoasRepository;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PessoasService {
    private final PessoasRepository pessoasRepository;

    public PessoasService(PessoasRepository pessoasRepository) {
        this.pessoasRepository = pessoasRepository;
    }

    public List<Person> listAll() {
        return pessoasRepository.findAll();
    }

    public List<Person> findByName(String name) {
        return pessoasRepository.findByName(name);
    }

    public Person findByIdOrThrowBadException(Long id) {
        return pessoasRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public Person save(PessoasPostRequestsBody pessoasPostRequestsBody) {
        //Anime anime = Anime.builder().name(animePostRequestsBody.getName()).build();
        return pessoasRepository.save(PessoasMapper.INSTANCE.toPessoas(pessoasPostRequestsBody));
    }

    public void delete(Long id) {
        pessoasRepository.delete(findByIdOrThrowBadException(id));
    }

    public void replace(PessoasPutRequestsBody pessoasPutRequestsBody, Long id) {
        Person savedPerson = findByIdOrThrowBadException(id);
        Person person = PessoasMapper.INSTANCE.toPessoas(pessoasPutRequestsBody);
        person.setId(savedPerson.getId());
        pessoasRepository.save(person);
    }
}