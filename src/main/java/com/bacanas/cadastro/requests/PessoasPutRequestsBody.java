package com.bacanas.cadastro.requests;

import lombok.Data;

@Data
public class PessoasPutRequestsBody {

    private String name;
    private String email;
    private String cpf;

}
