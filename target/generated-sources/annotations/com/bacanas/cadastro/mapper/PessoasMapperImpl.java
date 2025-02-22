package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import com.bacanas.cadastro.requests.PhoneDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-22T18:48:27-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PessoasMapperImpl extends PessoasMapper {

    @Override
    public Person toPessoas(PessoasPostRequestsBody pessoasPostRequestsBody) {
        if ( pessoasPostRequestsBody == null ) {
            return null;
        }

        Person person = new Person();

        person.setName( pessoasPostRequestsBody.getName() );
        person.setEmail( pessoasPostRequestsBody.getEmail() );
        person.setCpf( pessoasPostRequestsBody.getCpf() );
        List<Phone> list = pessoasPostRequestsBody.getPhones();
        if ( list != null ) {
            person.setPhones( new ArrayList<Phone>( list ) );
        }

        return person;
    }

    @Override
    public Person toPessoas(PessoasPutRequestsBody pessoasPutRequestsBody) {
        if ( pessoasPutRequestsBody == null ) {
            return null;
        }

        Person person = new Person();

        person.setId( pessoasPutRequestsBody.getId() );
        person.setName( pessoasPutRequestsBody.getName() );
        person.setEmail( pessoasPutRequestsBody.getEmail() );
        person.setCpf( pessoasPutRequestsBody.getCpf() );

        return person;
    }

    @Override
    public PersonDTO toPersonDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setId( person.getId() );
        personDTO.setName( person.getName() );
        personDTO.setEmail( person.getEmail() );
        personDTO.setCpf( person.getCpf() );
        personDTO.setPhones( phoneListToPhoneDTOList( person.getPhones() ) );

        return personDTO;
    }

    @Override
    public List<PersonDTO> toPersonDTOList(List<Person> people) {
        if ( people == null ) {
            return null;
        }

        List<PersonDTO> list = new ArrayList<PersonDTO>( people.size() );
        for ( Person person : people ) {
            list.add( toPersonDTO( person ) );
        }

        return list;
    }

    protected PhoneDTO phoneToPhoneDTO(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneDTO phoneDTO = new PhoneDTO();

        phoneDTO.setNumber( phone.getNumber() );
        phoneDTO.setTypePhone( map( phone.getTypePhone() ) );

        return phoneDTO;
    }

    protected List<PhoneDTO> phoneListToPhoneDTOList(List<Phone> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneDTO> list1 = new ArrayList<PhoneDTO>( list.size() );
        for ( Phone phone : list ) {
            list1.add( phoneToPhoneDTO( phone ) );
        }

        return list1;
    }
}
