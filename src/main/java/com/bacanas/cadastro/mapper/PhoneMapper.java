package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.requests.PhonePostRequestsBody;
import com.bacanas.cadastro.requests.PhonePutRequestsBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class PhoneMapper {
    public static final PhoneMapper INSTANCE = Mappers.getMapper(PhoneMapper.class);

    public abstract Phone toPhone(PhonePostRequestsBody phonePostRequestsBody);
    public abstract Phone toPhone(PhonePutRequestsBody phonePutRequestsBody);
    public abstract Phone toPhone(Phone phone);
}
