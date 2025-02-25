package com.bacanas.cadastro.repository;

import com.bacanas.cadastro.domain.TypePhone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypePhoneRepository extends JpaRepository<TypePhone, Long> {
    Optional<TypePhone> findByDescription(String description);
}
