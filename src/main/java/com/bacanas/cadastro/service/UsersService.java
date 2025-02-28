package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.exceptions.ConflictException;
import com.bacanas.cadastro.exceptions.NotFoundException;
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

    @Transactional
    public void save(UsersPostRequestsBody usersPostRequestsBody) {
        cleanAndValidatePersonData(usersPostRequestsBody);
        if (usersRepository.findByEmail(usersPostRequestsBody.getEmail()).isPresent()) {
            throw new ConflictException("Email already registered");
        }
        var user = new User();
        user.setName((usersPostRequestsBody.getName()));
        user.setEmail(usersPostRequestsBody.getEmail());
        user.setCpf(usersPostRequestsBody.getCpf());
        user.setPassword(passwordEncoder.encode(usersPostRequestsBody.getSenha()));
        usersRepository.save(user);
    }

    @Transactional
    public void delete(long id) {
        usersRepository.delete(findByIdOrThrowBadException(id));
    }

    @Transactional
    public LoginResponse replace(UsersPutRequestsBody usersPutRequestsBody) {
        cleanAndValidatePersonData(usersPutRequestsBody);
        User savedUserById = findByIdOrThrowBadException(usersPutRequestsBody.getId());
        Optional<User> userByEmailFromDb = usersRepository.findByEmail(usersPutRequestsBody.getEmail());
        if (userByEmailFromDb.isPresent() && !userByEmailFromDb.get().getId().equals(savedUserById.getId())) {
            throw new ConflictException("Email already registered");
        }
        User user = UsersMapper.INSTANCE.toUsers(usersPutRequestsBody);
        user.setId(savedUserById.getId());
        user.setPeople(savedUserById.getPeople());
        if (usersPutRequestsBody.getPassword() == null || usersPutRequestsBody.getPassword().isEmpty()) {
            user.setPassword(savedUserById.getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(usersPutRequestsBody.getPassword()));
        }
        usersRepository.save(user);
        return generateLoginResponse(user);
    }

    public LoginResponse login(LoginRequest loginRequest) {
        var user = findByEmail(loginRequest.email());
        if (!user.isLoginCorrect(loginRequest, passwordEncoder)) {
            throw new BadCredentialsException("Email or password is incorrect");
        }
        return generateLoginResponse(user);
    }

    private LoginResponse generateLoginResponse(User user) {
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

    private User findByEmail(String email) {
        return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    private void cleanAndValidatePersonData(Object userRequestBody) {
        if (userRequestBody instanceof UsersPostRequestsBody body) {
            body.setCpf(removeSpecialCharacters(body.getCpf()));
            body.setEmail(body.getEmail().trim());
            body.setName(body.getName().trim());
            validateCpfAndEmail(body.getCpf(), body.getEmail());
        } else if (userRequestBody instanceof UsersPutRequestsBody body) {
            body.setCpf(removeSpecialCharacters(body.getCpf()));
            body.setEmail(body.getEmail().trim());
            body.setName(body.getName().trim());
            validateCpfAndEmail(body.getCpf(), body.getEmail());
        }
    }

    private void validateCpfAndEmail(String cpf, String email) {
        if (cpf.length() != 11 || !cpf.matches("[0-9]+")) {
            throw new BadRequestException("Invalid CPF format");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (email == null || !email.matches(emailRegex)) {
            throw new BadRequestException("Invalid email format");
        }
    }

    private String removeSpecialCharacters(String input) {
        if (input != null) {
            return input.replaceAll("[^0-9]", "");
        }
        return input;
    }

    protected User findByIdOrThrowBadException(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }
}