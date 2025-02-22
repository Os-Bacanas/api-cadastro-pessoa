package com.bacanas.cadastro.requests;

import java.util.List;

public class PersonDTO {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private List<PhoneDTO> phones;

    public PersonDTO() {
    }

    public PersonDTO(Long id, String name, String email, String cpf, List<PhoneDTO> phones) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phones = phones;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<PhoneDTO> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDTO> phones) {
        this.phones = phones;
    }
}