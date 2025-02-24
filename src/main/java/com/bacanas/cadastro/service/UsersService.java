package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.mapper.UsersMapper;
import com.bacanas.cadastro.repository.UsersRepository;
import com.bacanas.cadastro.requests.LoginRequest;
import com.bacanas.cadastro.requests.LoginResponse;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class UsersService {
    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtEncoder jwtEncoder;

    public UsersService(UsersRepository usersRepository, BCryptPasswordEncoder passwordEncoder, JwtEncoder jwtEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtEncoder = jwtEncoder;
    }

    public List<User> listAll() {
        return usersRepository.findAll();
    }

    public List<User> findByName(String name) {
        return usersRepository.findByName(name);
    }

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public User findByIdOrThrowBadException(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Transactional
    public void save(UsersPostRequestsBody usersPostRequestsBody) {
        var userFormDb = usersRepository.findByEmail(usersPostRequestsBody.getEmail());
        if (userFormDb.isEmpty()) {
            var user = new User();
            user.setName((usersPostRequestsBody.getName()));
            user.setEmail(usersPostRequestsBody.getEmail());
            user.setCpf(usersPostRequestsBody.getCpf());
            user.setPassword(passwordEncoder.encode(usersPostRequestsBody.getSenha()));
            usersRepository.save(user);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "E-mail já cadastrado");
        }

    }

    @Transactional
    public void delete(long id) {
        usersRepository.delete(findByIdOrThrowBadException(id));
    }

    @Transactional
    public void replace(UsersPutRequestsBody usersPutRequestsBody) {
        User savedUserById = findByIdOrThrowBadException(usersPutRequestsBody.getId());
        Optional<User> userByEmailFromDb = usersRepository.findByEmail(usersPutRequestsBody.getEmail());
        if (userByEmailFromDb.isPresent() && !userByEmailFromDb.get().getId().equals(savedUserById.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        User user = UsersMapper.INSTANCE.toUsers(usersPutRequestsBody);
        user.setId(savedUserById.getId());

        if (usersPutRequestsBody.getPassword() == null || usersPutRequestsBody.getPassword().isEmpty()) {
            user.setPassword(savedUserById.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(usersPutRequestsBody.getPassword()));
        }
        usersRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var user = findByEmail(loginRequest.email());
        if (!user.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Email or password is incorrect");
        }
        var now = Instant.now();
        var expiresIn = 86400L;
        var claims = JwtClaimsSet.builder()
                .issuer("apiCadastro")
                .subject(user.getId().toString())
                .claim("name", user.getName())
                .claim("email", user.getEmail())
                .claim("cpf", user.getCpf())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();
        var tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(tokenValue, expiresIn);
    }
}