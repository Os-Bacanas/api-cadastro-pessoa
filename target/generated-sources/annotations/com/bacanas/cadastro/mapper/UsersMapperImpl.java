package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Users;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-20T11:12:53-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 23.0.2 (Oracle Corporation)"
)
@Component
public class UsersMapperImpl extends UsersMapper {

    @Override
    public Users toUsers(UsersPostRequestsBody usersPostRequestsBody) {
        if ( usersPostRequestsBody == null ) {
            return null;
        }

        Users users = new Users();

        users.setName( usersPostRequestsBody.getName() );
        users.setSenha( usersPostRequestsBody.getSenha() );
        users.setEmail( usersPostRequestsBody.getEmail() );
        users.setCpf( usersPostRequestsBody.getCpf() );

        return users;
    }

    @Override
    public Users toUsers(UsersPutRequestsBody UsersPutRequestsBody) {
        if ( UsersPutRequestsBody == null ) {
            return null;
        }

        Users users = new Users();

        users.setId( UsersPutRequestsBody.getId() );
        users.setName( UsersPutRequestsBody.getName() );
        users.setSenha( UsersPutRequestsBody.getSenha() );
        users.setEmail( UsersPutRequestsBody.getEmail() );
        users.setCpf( UsersPutRequestsBody.getCpf() );

        return users;
    }
}
