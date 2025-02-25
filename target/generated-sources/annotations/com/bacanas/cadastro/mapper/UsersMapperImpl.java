package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-25T14:32:03-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UsersMapperImpl extends UsersMapper {

    @Override
    public User toUsers(UsersPutRequestsBody UsersPutRequestsBody) {
        if ( UsersPutRequestsBody == null ) {
            return null;
        }

        User user = new User();

        user.setId( UsersPutRequestsBody.getId() );
        user.setName( UsersPutRequestsBody.getName() );
        user.setPassword( UsersPutRequestsBody.getPassword() );
        user.setEmail( UsersPutRequestsBody.getEmail() );
        user.setCpf( UsersPutRequestsBody.getCpf() );

        return user;
    }
}
