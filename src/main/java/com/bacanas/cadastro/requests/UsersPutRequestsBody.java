package com.bacanas.cadastro.requests;

import lombok.Data;

@Data
public class UsersPutRequestsBody {
    private Long id;
    private String name;
    private String senha;
    private String email;
    private String cpf;

}
