package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.requests.PersonDTO;
import com.bacanas.cadastro.requests.PhoneDTO;
import com.bacanas.cadastro.requests.TypePhoneDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-26T18:43:09-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PessoasMapperImpl extends PessoasMapper {

    @Override
    public PersonDTO toPersonDTO(Person person) {
        if ( person == null ) {
            return null;
        }

        PersonDTO personDTO = new PersonDTO();

        personDTO.setPhones( phoneListToPhoneDTOList( person.getPhones() ) );
        personDTO.setId( person.getId() );
        personDTO.setName( person.getName() );
        personDTO.setEmail( person.getEmail() );
        personDTO.setCpf( person.getCpf() );

        return personDTO;
    }

    @Override
    public PhoneDTO toPhoneDTO(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        PhoneDTO phoneDTO = new PhoneDTO();

        phoneDTO.setTypePhoneDTO( typePhoneToTypePhoneDTO( phone.getTypePhone() ) );
        phoneDTO.setNumber( phone.getNumber() );

        return phoneDTO;
    }

    @Override
    public Person toPerson(PersonDTO personDTO) {
        if ( personDTO == null ) {
            return null;
        }

        Person person = new Person();

        person.setId( personDTO.getId() );
        person.setName( personDTO.getName() );
        person.setEmail( personDTO.getEmail() );
        person.setCpf( personDTO.getCpf() );
        person.setPhones( phoneDTOListToPhoneList( personDTO.getPhones() ) );

        return person;
    }

    @Override
    public List<PersonDTO> toPersonDTOList(List<Person> byUserId) {
        if ( byUserId == null ) {
            return null;
        }

        List<PersonDTO> list = new ArrayList<PersonDTO>( byUserId.size() );
        for ( Person person : byUserId ) {
            list.add( toPersonDTO( person ) );
        }

        return list;
    }

    protected List<PhoneDTO> phoneListToPhoneDTOList(List<Phone> list) {
        if ( list == null ) {
            return null;
        }

        List<PhoneDTO> list1 = new ArrayList<PhoneDTO>( list.size() );
        for ( Phone phone : list ) {
            list1.add( toPhoneDTO( phone ) );
        }

        return list1;
    }

    protected TypePhoneDTO typePhoneToTypePhoneDTO(TypePhone typePhone) {
        if ( typePhone == null ) {
            return null;
        }

        TypePhoneDTO typePhoneDTO = new TypePhoneDTO();

        typePhoneDTO.setDescription( typePhone.getDescription() );

        return typePhoneDTO;
    }

    protected Phone phoneDTOToPhone(PhoneDTO phoneDTO) {
        if ( phoneDTO == null ) {
            return null;
        }

        Phone phone = new Phone();

        phone.setNumber( phoneDTO.getNumber() );

        return phone;
    }

    protected List<Phone> phoneDTOListToPhoneList(List<PhoneDTO> list) {
        if ( list == null ) {
            return null;
        }

        List<Phone> list1 = new ArrayList<Phone>( list.size() );
        for ( PhoneDTO phoneDTO : list ) {
            list1.add( phoneDTOToPhone( phoneDTO ) );
        }

        return list1;
    }
}
