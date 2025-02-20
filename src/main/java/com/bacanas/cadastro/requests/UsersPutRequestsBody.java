package com.bacanas.cadastro.requests;


public class UsersPutRequestsBody {
    private Long id;
    private String name;
    private String senha;
    private String email;
    private String cpf;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }
}