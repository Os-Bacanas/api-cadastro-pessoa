package com.bacanas.cadastro.mapper;


import com.bacanas.cadastro.domain.Pessoas;
import com.bacanas.cadastro.requests.PessoasPostRequestsBody;
import com.bacanas.cadastro.requests.PessoasPutRequestsBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PessoasMapper {
    public static final PessoasMapper INSTANCE = Mappers.getMapper(PessoasMapper.class);

    public abstract Pessoas toPessoas(PessoasPostRequestsBody pessoasPostRequestsBody);
    public abstract Pessoas toPessoas(PessoasPutRequestsBody pessoasPutRequestsBody);
}