package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.requests.PhoneDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PhoneMapper {
    public static final PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    public abstract Phone toPhone(PhoneDTO phone);
}
