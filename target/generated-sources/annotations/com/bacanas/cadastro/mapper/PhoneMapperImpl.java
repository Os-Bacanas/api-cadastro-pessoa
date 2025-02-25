package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.requests.PhoneDTO;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T16:00:37-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PhoneMapperImpl extends PhoneMapper {

    @Override
    public Phone toPhone(PhoneDTO phone) {
        if ( phone == null ) {
            return null;
        }

        Phone phone1 = new Phone();

        phone1.setNumber( phone.getNumber() );

        return phone1;
    }
}
