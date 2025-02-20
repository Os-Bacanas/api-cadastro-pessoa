package com.bacanas.cadastro.repository;

import com.bacanas.cadastro.domain.Pessoas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoasRepository extends JpaRepository<Pessoas, Long> {
    List<Pessoas> findByName(String name);
    String name(String name);
    //List<Pessoas> findbyUserId(long userId);

    // List<Pessoas> id(Long id);

    //List<Pessoas> findAllById(Long userId);
}
