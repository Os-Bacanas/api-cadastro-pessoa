package com.bacanas.cadastro.config;

import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

@Configuration
public class AdminUserConfig implements CommandLineRunner {
    private UsersRepository usersRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Optional<User> userAdmin = usersRepository.findByEmail("admin@dev.com");
        if (userAdmin.isEmpty()) {
            var user = new User();
            user.setName("admin");
            user.setEmail("admin@dev.com");
            user.setCpf("111.111.111-11");
            user.setPassword(passwordEncoder.encode("123"));
            usersRepository.save(user);
        }
    }
}