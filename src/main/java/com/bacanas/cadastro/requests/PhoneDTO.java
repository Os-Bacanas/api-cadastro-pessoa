package com.bacanas.cadastro.requests;

public class PhoneDTO {
    private String number;
    private String typePhone;

    public PhoneDTO() {
    }

    public PhoneDTO(String number, String typePhone) {
        this.number = number;
        this.typePhone = typePhone;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTypePhone() {
        return typePhone;
    }

    public void setTypePhone(String typePhone) {
        this.typePhone = typePhone;
    }
}
