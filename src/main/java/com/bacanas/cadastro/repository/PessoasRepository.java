package com.bacanas.cadastro.repository;

import com.bacanas.cadastro.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PessoasRepository extends JpaRepository<Person, Long> {
    List<Person> findByName(String name);
    List<Person> findByEmailIn(List<String> emails);
    Optional<Person> findByEmail(String email);
}