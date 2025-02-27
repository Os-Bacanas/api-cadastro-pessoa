package com.bacanas.cadastro.requests;

public class PhoneDTO {
    private String number;
    private TypePhoneDTO typePhoneDTO;

    public PhoneDTO() {
    }

    public PhoneDTO(String number, TypePhoneDTO typePhoneDTO) {
        this.number = number;
        this.typePhoneDTO = typePhoneDTO;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public TypePhoneDTO getTypePhoneDTO() {
        return typePhoneDTO;
    }

    public void setTypePhoneDTO(TypePhoneDTO typePhoneDTO) {
        this.typePhoneDTO = typePhoneDTO;
    }
}