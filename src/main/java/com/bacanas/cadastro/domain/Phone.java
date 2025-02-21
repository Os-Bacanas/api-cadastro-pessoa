package com.bacanas.cadastro.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "phones")
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private TypePhone typePhone;

    public Phone() {
    }

    public Phone(Long id, String number, Person person, TypePhone typePhone) {
        this.id = id;
        this.number = number;
        this.person = person;
        this.typePhone = typePhone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public TypePhone getTypePhone() {
        return typePhone;
    }

    public void setTypePhone(TypePhone typePhone) {
        this.typePhone = typePhone;
    }
}