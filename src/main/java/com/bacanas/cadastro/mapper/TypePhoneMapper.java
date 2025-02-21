package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.requests.TypePhonePostRequestsBody;
import com.bacanas.cadastro.requests.TypePhonePutRequestsBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class TypePhoneMapper {
    public static final TypePhoneMapper INSTANCE = Mappers.getMapper(TypePhoneMapper.class);

    public abstract TypePhone toTypePhone(TypePhonePostRequestsBody TypephonePostRequestsBody);
    public abstract TypePhone toTypePhone(TypePhonePutRequestsBody TypephonePutRequestsBody);

    public TypePhone toTypePhone(String description) {
        TypePhone typePhone = new TypePhone();
        typePhone.setDescription(description);
        return typePhone;
    }
}