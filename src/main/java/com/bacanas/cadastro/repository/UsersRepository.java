package com.bacanas.cadastro.repository;

import com.bacanas.cadastro.domain.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Long> {
    List<Users> findByName(String name);
    String name(String name);
}
