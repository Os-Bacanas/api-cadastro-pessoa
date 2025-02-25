package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.repository.TypePhoneRepository;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PhoneDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Mapper(componentModel = "spring", uses = {TypePhoneRepository.class})
public abstract class PessoasMapper {

    @Autowired
    private TypePhoneRepository typePhoneRepository;

    public static final PessoasMapper INSTANCE = Mappers.getMapper(PessoasMapper.class);

    @Mapping(target = "phones", source = "phones")
    public abstract PersonDTO toPersonDTO(Person person);

    @Mapping(target = "typePhoneDTO", source = "typePhone")
    public abstract PhoneDTO toPhoneDTO(Phone phone);

    public abstract Person toPerson(PersonDTO personDTO);

    public abstract Person toPerson(PessoasPostRequestsBody pessoasPostRequestsBody);

    public TypePhone map(String description) {
        TypePhone typePhone = new TypePhone();
        typePhone.setDescription(description);
        return typePhone;
    }
    public TypePhone mapFromRepository(String description) {
        Optional<TypePhone> typePhone = typePhoneRepository.findByDescription(description);
        return typePhone.orElseGet(() -> {
            TypePhone newTypePhone = new TypePhone();
            newTypePhone.setDescription(description);
            return newTypePhone;
        });
    }
}
