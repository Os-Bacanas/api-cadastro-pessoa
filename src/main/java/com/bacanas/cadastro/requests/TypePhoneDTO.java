package com.bacanas.cadastro.requests;

public class TypePhoneDTO {
    private String description;

    public TypePhoneDTO(String description) {
        this.description = description;
    }

    public TypePhoneDTO() {
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
