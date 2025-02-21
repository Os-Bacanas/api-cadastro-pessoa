package com.bacanas.cadastro.domain;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "type_phone")
public class TypePhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "typePhone")
    private List<Phone> phones;
    private String description;

    public TypePhone() {
    }

    public TypePhone(Long id, List<Phone> phones, String description) {
        this.id = id;
        this.phones = phones;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}