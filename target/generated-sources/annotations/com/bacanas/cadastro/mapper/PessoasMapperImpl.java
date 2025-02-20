package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Pessoas;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T11:12:53-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PessoasMapperImpl extends PessoasMapper {

    @Override
    public Pessoas toPessoas(PessoasPostRequestsBody pessoasPostRequestsBody) {
        if ( pessoasPostRequestsBody == null ) {
            return null;
        }

        Pessoas pessoas = new Pessoas();

        pessoas.setName( pessoasPostRequestsBody.getName() );
        pessoas.setEmail( pessoasPostRequestsBody.getEmail() );
        pessoas.setCpf( pessoasPostRequestsBody.getCpf() );

        return pessoas;
    }

    @Override
    public Pessoas toPessoas(PessoasPutRequestsBody pessoasPutRequestsBody) {
        if ( pessoasPutRequestsBody == null ) {
            return null;
        }

        Pessoas pessoas = new Pessoas();

        pessoas.setId( pessoasPutRequestsBody.getId() );
        pessoas.setName( pessoasPutRequestsBody.getName() );
        pessoas.setEmail( pessoasPutRequestsBody.getEmail() );
        pessoas.setCpf( pessoasPutRequestsBody.getCpf() );

        return pessoas;
    }
}
