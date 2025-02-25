package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.mapper.PessoasMapper;
import com.bacanas.cadastro.mapper.PhoneMapper;
import com.bacanas.cadastro.mapper.TypePhoneMapper;
import com.bacanas.cadastro.repository.PessoasRepository;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import jakarta.transaction.Transactional;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PessoasService {
    private final PessoasRepository pessoasRepository;
    private final UsersService usersService;

    public PessoasService(PessoasRepository pessoasRepository, UsersService usersService) {
        this.pessoasRepository = pessoasRepository;
        this.usersService = usersService;
    }

    public List<PersonDTO> listAll() {
        List<Person> people = pessoasRepository.findAll();
        return PessoasMapper.INSTANCE.toPersonDTOList(people);
    }

    public List<Person> findByName(String name) {
        return pessoasRepository.findByName(name);
    }

    @Transactional
    public void save(PessoasPostRequestsBody pessoasPostRequestsBody, JwtAuthenticationToken token) {
        var user = usersService.findByIdOrThrowBadException(Long.parseLong(token.getName()));
        Person person = PessoasMapper.INSTANCE.toPessoas(pessoasPostRequestsBody);
        person.setUser(user);

        List<Phone> phones = new ArrayList<>();
        for (Phone phoneRequest : pessoasPostRequestsBody.getPhones()) {
            Phone phone = PhoneMapper.INSTANCE.toPhone(phoneRequest);
            TypePhone typePhone = TypePhoneMapper.INSTANCE.toTypePhone(phoneRequest.getTypePhone().getDescription());
            phone.setTypePhone(typePhone);
            phone.setPerson(person);
            phones.add(phone);
        }
        person.setPhones(phones);
        pessoasRepository.save(person);
    }

    public List<Person> findByEmailsOrThrowBadException(List<String> emails) {
        List<Person> pessoas = pessoasRepository.findByEmailIn(emails);
        if (pessoas.size() != emails.size()) {
            throw new BadRequestException("Some users not found");
        }
        return pessoas;
    }

    public Person findByEmailOrThrowBadException(String email) {
        return pessoasRepository.findByEmail(email).orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Transactional
    public void delete(List<String> emails) {
        List<Person> pessoas = findByEmailsOrThrowBadException(emails);
        pessoasRepository.deleteAll(pessoas);
    }

    @Transactional
    public void replace(PersonDTO personDTO) {
        Person savedPerson = findByEmailOrThrowBadException(personDTO.getEmail());
        Person person = PessoasMapper.INSTANCE.toPerson(personDTO);
        person.setId(savedPerson.getId());
        if (person.getPhones() != null) {
            for (Phone phone : person.getPhones()) {
                phone.setPerson(person);
            }
        }
        pessoasRepository.save(person);
    }
}