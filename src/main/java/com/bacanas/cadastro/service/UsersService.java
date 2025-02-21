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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

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
        var userFormDb = findByEmail(usersPostRequestsBody.getEmail());
        var user = new User();
        user.setName((usersPostRequestsBody.getName()));
        user.setEmail(usersPostRequestsBody.getEmail());
        user.setCpf(usersPostRequestsBody.getCpf());
        user.setPassword(usersPostRequestsBody.getSenha());
        usersRepository.save(user);
    }

    @Transactional
    public void delete(long id) {
        usersRepository.delete(findByIdOrThrowBadException(id));
    }

    @Transactional
    public void replace(UsersPutRequestsBody usersPutRequestsBody) {
        User savedUser = findByIdOrThrowBadException(usersPutRequestsBody.getId());
        User user = UsersMapper.INSTANCE.toUsers(usersPutRequestsBody);
        user.setId(savedUser.getId());
        usersRepository.save(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var user = findByEmail(loginRequest.email());
        if (!user.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Email or password is invalid");
        }
        var now = Instant.now();
        var expiresIn = 300L;
        var claims = JwtClaimsSet.builder()
                .issuer("apiCadastro")
                .subject(user.getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();
        var tokenValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return new LoginResponse(tokenValue, expiresIn);
    }
}