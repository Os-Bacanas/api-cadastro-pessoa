package com.bacanas.cadastro.repository;

import com.bacanas.cadastro.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface UsersRepository extends JpaRepository<User, Long> {
    List<User> findByName(String name);
    String name(String name);
}