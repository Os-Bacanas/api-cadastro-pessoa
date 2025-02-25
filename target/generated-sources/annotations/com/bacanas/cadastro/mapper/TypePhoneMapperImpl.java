package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.requests.TypePhoneDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T16:18:15-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class TypePhoneMapperImpl extends TypePhoneMapper {

    @Override
    public TypePhone toTypePhone(TypePhoneDTO typePhoneDTO) {
        if ( typePhoneDTO == null ) {
            return null;
        }

        TypePhone typePhone = new TypePhone();

        typePhone.setDescription( typePhoneDTO.getDescription() );

        return typePhone;
    }
}
