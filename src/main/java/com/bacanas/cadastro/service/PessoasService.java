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
import com.bacanas.cadastro.repository.PhoneRepository;
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
    private final PhoneRepository phoneRepository;

    public PessoasService(PessoasRepository pessoasRepository, UsersService usersService, TypePhoneRepository typePhoneRepository, PhoneRepository phoneRepository) {
        this.pessoasRepository = pessoasRepository;
        this.usersService = usersService;
        this.typePhoneRepository = typePhoneRepository;
        this.phoneRepository = phoneRepository;
    }

    public List<PersonDTO> listByUser(JwtAuthenticationToken token) {
        return PessoasMapper.INSTANCE.toPersonDTOList(pessoasRepository.findByUserId(Long.parseLong(token.getName())));
    }

    @Transactional
    public void save(PersonDTO personDTO, JwtAuthenticationToken token) {
        var user = usersService.findByIdOrThrowBadException(Long.parseLong(token.getName()));
        cleanAndValidatePersonData(personDTO);
        checkEmailUnique(personDTO.getEmail(), null);
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
        List<Phone> updatedPhones = mapPhones(personDTO.getPhones(), person);
        person.setPhones(updatedPhones);
        pessoasRepository.save(person);
        removeUnusedTypePhones(updatedPhones);
    }

    private void removeUnusedTypePhones(List<Phone> updatedPhones) {
        for (Phone phone : updatedPhones) {
            TypePhone typePhone = phone.getTypePhone();
            List<Phone> phonesLinkedToTypePhone = phoneRepository.findByTypePhone(typePhone);
            if (phonesLinkedToTypePhone.isEmpty()) {
                typePhoneRepository.delete(typePhone);
            }
        }
    }

    private List<Phone> mapPhones(List<PhoneDTO> phoneDTOs, Person person) {
        List<Phone> phones = new ArrayList<>();
        for (PhoneDTO phoneDTO : phoneDTOs) {
            Phone phone = PhoneMapper.INSTANCE.toPhone(phoneDTO);
            TypePhone typePhone = findOrCreateTypePhone(phoneDTO.getTypePhoneDTO().getDescription());
            if (phone.getTypePhone() == null || !phone.getTypePhone().equals(typePhone)) {
                phone.setTypePhone(typePhone);
            }
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
        validateCpf(personDTO.getCpf());
        validateEmail(personDTO.getEmail());
        personDTO.setPhones(cleanAndFormatPhoneNumbers(personDTO.getPhones()));
    }

    private void validateCpf(String cpf) {
        if (cpf.length() != 11 || !cpf.matches("[0-9]{11}")) {
            throw new BadRequestException("Invalid CPF format");
        }
    }

    private void validateEmail(String email) {
        if (email == null || email.isEmpty()) {
            throw new BadRequestException("Email is required");
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            throw new BadRequestException("Invalid email format");
        }
    }

    private List<PhoneDTO> cleanAndFormatPhoneNumbers(List<PhoneDTO> phoneDTOs) {
        for (PhoneDTO phoneDTO : phoneDTOs) {
            if (phoneDTO.getNumber() != null) {
                phoneDTO.setNumber(removeSpecialCharacters(phoneDTO.getNumber()));
            }
        }
        return phoneDTOs;
    }

    private String removeSpecialCharacters(String input) {
        return input != null ? input.replaceAll("[^0-9]", "") : input;
    }
}