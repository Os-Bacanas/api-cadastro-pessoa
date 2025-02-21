package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Person;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T18:29:32-0300",
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
}
