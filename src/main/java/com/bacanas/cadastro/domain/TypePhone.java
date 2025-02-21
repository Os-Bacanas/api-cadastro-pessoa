package com.bacanas.cadastro.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "type_phone")
public class TypePhone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "phone_id")
    private Phone phone;


    public TypePhone() {
    }

    public TypePhone(Long id, Phone phone) {
        this.id = id;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }
}
