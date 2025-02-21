package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Phone;
import com.bacanas.cadastro.requests.PhonePostRequestsBody;
import com.bacanas.cadastro.requests.PhonePutRequestsBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-21T18:43:17-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class PhoneMapperImpl extends PhoneMapper {

    @Override
    public Phone toPhone(PhonePostRequestsBody phonePostRequestsBody) {
        if ( phonePostRequestsBody == null ) {
            return null;
        }

        Phone phone = new Phone();

        phone.setNumber( phonePostRequestsBody.getNumber() );

        return phone;
    }

    @Override
    public Phone toPhone(PhonePutRequestsBody phonePutRequestsBody) {
        if ( phonePutRequestsBody == null ) {
            return null;
        }

        Phone phone = new Phone();

        phone.setId( phonePutRequestsBody.getId() );
        phone.setNumber( phonePutRequestsBody.getNumber() );

        return phone;
    }

    @Override
    public Phone toPhone(Phone phone) {
        if ( phone == null ) {
            return null;
        }

        Phone phone1 = new Phone();

        phone1.setId( phone.getId() );
        phone1.setNumber( phone.getNumber() );
        phone1.setPerson( phone.getPerson() );
        phone1.setTypePhone( phone.getTypePhone() );

        return phone1;
    }
}
