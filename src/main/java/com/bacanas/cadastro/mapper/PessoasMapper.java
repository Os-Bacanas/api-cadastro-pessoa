package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class PessoasMapper {
    public static final PessoasMapper INSTANCE = Mappers.getMapper(PessoasMapper.class);

    public abstract Person toPessoas(PessoasPostRequestsBody pessoasPostRequestsBody);
    public abstract Person toPessoas(PessoasPutRequestsBody pessoasPutRequestsBody);
    public abstract PersonDTO toPersonDTO(Person person);
    public abstract List<PersonDTO> toPersonDTOList(List<Person> people);

    //mapeia typePhone para string
    public String map(TypePhone typePhone) {
        return typePhone != null ? typePhone.getDescription() : null;
    }
}