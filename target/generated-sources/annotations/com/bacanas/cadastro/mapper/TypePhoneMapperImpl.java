package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.TypePhone;
import com.bacanas.cadastro.requests.TypePhonePostRequestsBody;
import com.bacanas.cadastro.requests.TypePhonePutRequestsBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-21T18:43:17-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class TypePhoneMapperImpl extends TypePhoneMapper {

    @Override
    public TypePhone toTypePhone(TypePhonePostRequestsBody TypephonePostRequestsBody) {
        if ( TypephonePostRequestsBody == null ) {
            return null;
        }

        TypePhone typePhone = new TypePhone();

        typePhone.setDescription( TypephonePostRequestsBody.getDescription() );

        return typePhone;
    }

    @Override
    public TypePhone toTypePhone(TypePhonePutRequestsBody TypephonePutRequestsBody) {
        if ( TypephonePutRequestsBody == null ) {
            return null;
        }

        TypePhone typePhone = new TypePhone();

        typePhone.setId( TypephonePutRequestsBody.getId() );
        typePhone.setDescription( TypephonePutRequestsBody.getDescription() );

        return typePhone;
    }
}
