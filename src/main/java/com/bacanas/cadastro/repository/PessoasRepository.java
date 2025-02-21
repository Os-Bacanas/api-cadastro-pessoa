package com.bacanas.cadastro.repository;

import com.bacanas.cadastro.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PessoasRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);
    String name(String name);
}