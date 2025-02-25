package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.requests.PhoneDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T17:07:07-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PhoneMapperImpl extends PhoneMapper {

    @Override
    public Phone toPhone(PhoneDTO phoneDTO) {
        if ( phoneDTO == null ) {
            return null;
        }

        Phone phone = new Phone();

        phone.setNumber( phoneDTO.getNumber() );

        return phone;
    }
}
