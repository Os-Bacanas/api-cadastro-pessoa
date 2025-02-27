package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.exceptions.ConflictException;
import com.bacanas.cadastro.exceptions.NotFoundException;
import com.bacanas.cadastro.mapper.PessoasMapper;
import com.bacanas.cadastro.mapper.PhoneMapper;
import com.bacanas.cadastro.repository.PessoasRepository;
import com.bacanas.cadastro.repository.TypePhoneRepository;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PhoneDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PessoasService {
    private final PessoasRepository pessoasRepository;
    private final UsersService usersService;
    private final TypePhoneRepository typePhoneRepository;

    public PessoasService(PessoasRepository pessoasRepository, UsersService usersService, TypePhoneRepository typePhoneRepository) {
        this.pessoasRepository = pessoasRepository;
        this.usersService = usersService;
        this.typePhoneRepository = typePhoneRepository;
    }

    public List<PersonDTO> listByUser(JwtAuthenticationToken token) {
        return PessoasMapper.INSTANCE.toPersonDTOList(pessoasRepository.findByUserId(Long.parseLong(token.getName())));
    }

    @Transactional
    public void save(PersonDTO personDTO, JwtAuthenticationToken token) {
        var user = usersService.findByIdOrThrowBadException(Long.parseLong(token.getName()));
        cleanAndValidatePersonData(personDTO);
        checkEmailUnique(personDTO.getEmail(), null);  // Verifica se o e-mail é único
        Person person = PessoasMapper.INSTANCE.toPerson(personDTO);
        person.setUser(user);
        person.setPhones(mapPhones(personDTO.getPhones(), person));
        pessoasRepository.save(person);
    }

    @Transactional
    public void delete(List<String> emails) {
        List<Person> pessoas = findByEmailsOrThrowNotFoundException(emails);
        pessoasRepository.deleteAll(pessoas);
    }

    @Transactional
    public void replace(PersonDTO personDTO) {
        Person savedPerson = findByIdOrThrowNotFoundException(personDTO.getId());
        checkEmailUnique(personDTO.getEmail(), savedPerson.getId());
        cleanAndValidatePersonData(personDTO);
        Person person = PessoasMapper.INSTANCE.toPerson(personDTO);
        person.setId(savedPerson.getId());
        person.setUser(savedPerson.getUser());
        person.setPhones(mapPhones(personDTO.getPhones(), person));
        pessoasRepository.save(person);
    }

    private List<Phone> mapPhones(List<PhoneDTO> phoneDTOs, Person person) {
        List<Phone> phones = new ArrayList<>();
        for (PhoneDTO phoneDTO : phoneDTOs) {
            Phone phone = PhoneMapper.INSTANCE.toPhone(phoneDTO);
            phone.setTypePhone(findOrCreateTypePhone(phoneDTO.getTypePhoneDTO().getDescription()));
            phone.setPerson(person);
            phones.add(phone);
        }
        return phones;
    }

    private void checkEmailUnique(String email, Long currentPersonId) {
        Optional<Person> personFromDb = pessoasRepository.findByEmail(email);
        if (personFromDb.isPresent() && !personFromDb.get().getId().equals(currentPersonId)) {
            throw new ConflictException("Email already registered");
        }
    }

    private TypePhone findOrCreateTypePhone(String description) {
        return typePhoneRepository.findByDescription(description)
                .orElseGet(() -> typePhoneRepository.save(new TypePhone(description)));
    }

    private Person findByIdOrThrowNotFoundException(Long id) {
        return pessoasRepository.findById(id).orElseThrow(() -> new NotFoundException("Person not found"));
    }

    private List<Person> findByEmailsOrThrowNotFoundException(List<String> emails) {
        List<Person> pessoas = pessoasRepository.findByEmailIn(emails);
        if (pessoas.size() != emails.size()) {
            throw new NotFoundException("Some people not found");
        }
        return pessoas;
    }

    private void cleanAndValidatePersonData(PersonDTO personDTO) {
        personDTO.setCpf(removeSpecialCharacters(personDTO.getCpf()));
        personDTO.setEmail(personDTO.getEmail().trim());
        personDTO.setName(personDTO.getName().trim());

        if (personDTO.getCpf().length() != 11 || !isValidCpf(personDTO.getCpf())) {
            throw new BadRequestException("Invalid CPF format");
        }

        if (personDTO.getEmail() == null || personDTO.getEmail().isEmpty()) {
            throw new BadRequestException("Email is required");
        }

        // Validação do e-mail usando uma expressão regular robusta
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!personDTO.getEmail().matches(emailRegex)) {
            throw new BadRequestException("Invalid email format");
        }
    }

    private boolean isValidCpf(String cpf) {
        // Lógica de validação de CPF (dígitos verificadores, etc.)
        // Exemplo de verificação básica:
        return cpf.matches("[0-9]{11}");
    }

    private String removeSpecialCharacters(String input) {
        return input != null ? input.replaceAll("[^0-9]", "") : input;
    }
}
