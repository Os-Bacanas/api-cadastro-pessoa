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
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PessoasService {
    private final PessoasRepository pessoasRepository;
    private final ModelMapper modelMapper;
    private final UsersService usersService;
    private final PessoasMapper pessoasMapper;
    private final PhoneMapper phoneMapper;
    private final TypePhoneMapper typePhoneMapper;

    public PessoasService(PessoasRepository pessoasRepository, ModelMapper modelMapper, UsersService usersService, PessoasMapper pessoasMapper, PhoneMapper phoneMapper, TypePhoneMapper typePhoneMapper) {
        this.pessoasRepository = pessoasRepository;
        this.modelMapper = modelMapper;
        this.usersService = usersService;
        this.pessoasMapper = pessoasMapper;
        this.phoneMapper = phoneMapper;
        this.typePhoneMapper = typePhoneMapper;
    }

    public List<PersonDTO> listAll() {
        List<Person> people = pessoasRepository.findAll();
        return people.stream()
                .map(person -> modelMapper.map(person, PersonDTO.class))
                .collect(Collectors.toList());
    }

    public List<Person> findByName(String name) {
        return pessoasRepository.findByName(name);
    }

    public Person findByIdOrThrowBadException(Long id) {
        return pessoasRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }

    @Transactional
    public void save(PessoasPostRequestsBody pessoasPostRequestsBody, JwtAuthenticationToken token) {
        var user = usersService.findByIdOrThrowBadException(Long.parseLong(token.getName()));
        Person person = pessoasMapper.toPessoas(pessoasPostRequestsBody);
        person.setUser(user);

        List<Phone> phones = new ArrayList<>();
        for (Phone phoneRequest : pessoasPostRequestsBody.getPhones()) {
            Phone phone = phoneMapper.toPhone(phoneRequest);
            TypePhone typePhone = typePhoneMapper.toTypePhone(phoneRequest.getTypePhone().getDescription());
            phone.setTypePhone(typePhone);
            phone.setPerson(person);
            phones.add(phone);
        }
        person.setPhones(phones);
        pessoasRepository.save(person);
    }

    @Transactional
    public void delete(Long id) {
        pessoasRepository.delete(findByIdOrThrowBadException(id));
    }

    @Transactional
    public void replace(PessoasPutRequestsBody pessoasPutRequestsBody, Long id) {
        Person savedPerson = findByIdOrThrowBadException(id);
        Person person = PessoasMapper.INSTANCE.toPessoas(pessoasPutRequestsBody);
        person.setId(savedPerson.getId());
        pessoasRepository.save(person);
    }
}