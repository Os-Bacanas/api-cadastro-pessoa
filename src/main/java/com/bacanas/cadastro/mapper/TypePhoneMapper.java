package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.TypePhone;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TypePhoneMapper {
    public static final TypePhoneMapper INSTANCE = Mappers.getMapper(TypePhoneMapper.class);

    public TypePhone toTypePhone(String description) {
        TypePhone typePhone = new TypePhone();
        typePhone.setDescription(description);
        return typePhone;
    }
}