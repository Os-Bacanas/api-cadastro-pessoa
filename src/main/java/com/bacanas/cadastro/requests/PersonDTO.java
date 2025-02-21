package com.bacanas.cadastro.requests;

public class PersonDTO {
    private Long id;
    private String name;
    private String email;
    private String cpf;
    private String phone;
    private String typePhone;

    public PersonDTO() {
    }

    public PersonDTO(Long id, String name, String email, String cpf, String phone, String typePhone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.cpf = cpf;
        this.phone = phone;
        this.typePhone = typePhone;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTypePhone() {
        return typePhone;
    }

    public void setTypePhone(String typePhone) {
        this.typePhone = typePhone;
    }
}
