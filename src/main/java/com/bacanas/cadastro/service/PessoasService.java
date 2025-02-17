package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.Pessoas;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.mapper.PessoasMapper;
import com.bacanas.cadastro.repository.PessoasRepository;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PessoasService {
    private final PessoasRepository pessoasRepository;
    public List<Pessoas> listAll() {
        return pessoasRepository.findAll();

    }
    public List<Pessoas> findByName(String name) {
        return pessoasRepository.findByName(name);

    }public Pessoas  findByIdOrThrowBadException(Long id) {
        return pessoasRepository.findById(id)
                .orElseThrow(()-> new BadRequestException("Users not found"));

    }
    public Pessoas save (PessoasPostRequestsBody pessoasPostRequestsBody) {
        //Anime anime = Anime.builder().name(animePostRequestsBody.getName()).build();

        return pessoasRepository.save(PessoasMapper.INSTANCE.toPessoas(pessoasPostRequestsBody));

    }public void delete (Long id){

        pessoasRepository.delete(findByIdOrThrowBadException(id));
    }
    public void replace (PessoasPutRequestsBody pessoasPutRequestsBody) {
        Pessoas savedPessoas= findByIdOrThrowBadException(pessoasPutRequestsBody.getId());

        Pessoas pessoas = PessoasMapper.INSTANCE.toPessoas(pessoasPutRequestsBody);
        pessoas.setId(savedPessoas.getId());
        pessoasRepository.save(pessoas);

    }


}
