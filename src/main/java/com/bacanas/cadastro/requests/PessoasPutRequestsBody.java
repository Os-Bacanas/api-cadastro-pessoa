package com.bacanas.cadastro.requests;

import lombok.Data;

@Data
public class PessoasPutRequestsBody {
    private Long id;
    private String name;
    private String email;
    private String cpf;

}
