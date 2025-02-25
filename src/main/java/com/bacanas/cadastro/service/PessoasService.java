package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.mapper.PessoasMapper;
import com.bacanas.cadastro.mapper.PhoneMapper;
import com.bacanas.cadastro.mapper.TypePhoneMapper;
import com.bacanas.cadastro.repository.PessoasRepository;
import com.bacanas.cadastro.repository.TypePhoneRepository;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PhoneDTO;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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

    public List<PersonDTO> listAll() {
        List<Person> people = pessoasRepository.findAll();
        List<PersonDTO> personDTOList = new ArrayList<>();
        for (Person person : people) {
            personDTOList.add(PessoasMapper.INSTANCE.toPersonDTO(person));
        }
        return personDTOList;
    }

    public List<Person> findByName(String name) {
        return pessoasRepository.findByName(name);
    }

    @Transactional
    public void save(PersonDTO personDTO, JwtAuthenticationToken token) {
        var user = usersService.findByIdOrThrowBadException(Long.parseLong(token.getName()));
        Person person = PessoasMapper.INSTANCE.toPerson(personDTO);
        person.setUser(user);
        List<Phone> phones = new ArrayList<>();
        for (PhoneDTO phoneRequest : personDTO.getPhones()) {
            Phone phone = PhoneMapper.INSTANCE.toPhone(phoneRequest);
            TypePhone typePhone = TypePhoneMapper.INSTANCE.toTypePhone(phoneRequest.getTypePhoneDTO().getDescription());
            phone.setTypePhone(typePhone);
            phone.setPerson(person);
            phones.add(phone);
        }
        person.setPhones(phones);
        pessoasRepository.save(person);
    }

    private List<Person> findByEmailsOrThrowBadException(List<String> emails) {
        List<Person> pessoas = pessoasRepository.findByEmailIn(emails);
        if (pessoas.size() != emails.size()) {
            throw new BadRequestException("Some people not found");
        }
        return pessoas;
    }

    @Transactional
    public void delete(List<String> emails) {
        List<Person> pessoas = findByEmailsOrThrowBadException(emails);
        pessoasRepository.deleteAll(pessoas);
    }

    private Person findByIdOrThrowBadException(Long id) {
        return pessoasRepository.findById(id).orElseThrow(() -> new BadRequestException("Person not found"));
    }

    @Transactional
    public void replace(PersonDTO personDTO) {
        Person savedPerson = findByIdOrThrowBadException(personDTO.getId());
        Optional<Person> userByEmailFromDb = pessoasRepository.findByEmail(personDTO.getEmail());
        if (userByEmailFromDb.isPresent() && !userByEmailFromDb.get().getId().equals(savedPerson.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email já cadastrado");
        }
        Person person = PessoasMapper.INSTANCE.toPerson(personDTO);
        person.setId(savedPerson.getId());
        List<Phone> phones = new ArrayList<>();
        for (PhoneDTO phoneRequest : personDTO.getPhones()) {
            Phone phone = PhoneMapper.INSTANCE.toPhone(phoneRequest);
            TypePhone typePhone = findOrCreateTypePhone(phoneRequest.getTypePhoneDTO().getDescription());
            phone.setTypePhone(typePhone);
            phone.setPerson(person);
            phones.add(phone);
        }
        person.setPhones(phones);
        pessoasRepository.save(person);
    }

    private TypePhone findOrCreateTypePhone(String description) {
        Optional<TypePhone> existingTypePhone = typePhoneRepository.findByDescription(description);

        return existingTypePhone.orElseGet(() -> {
            TypePhone newTypePhone = new TypePhone();
            newTypePhone.setDescription(description);
            return typePhoneRepository.save(newTypePhone);
        });
    }
}