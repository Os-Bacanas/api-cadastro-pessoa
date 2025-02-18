package com.bacanas.cadastro.mapper;

import com.bacanas.cadastro.domain.Users;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-02-17T17:36:18-0400",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.1 (Oracle Corporation)"
)
@Component
public class UsersMapperImpl extends UsersMapper {

    @Override
    public Users toUsers(UsersPostRequestsBody usersPostRequestsBody) {
        if ( usersPostRequestsBody == null ) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.name( usersPostRequestsBody.getName() );
        users.senha( usersPostRequestsBody.getSenha() );
        users.email( usersPostRequestsBody.getEmail() );
        users.cpf( usersPostRequestsBody.getCpf() );

        return users.build();
    }

    @Override
    public Users toUsers(UsersPutRequestsBody UsersPutRequestsBody) {
        if ( UsersPutRequestsBody == null ) {
            return null;
        }

        Users.UsersBuilder users = Users.builder();

        users.id( UsersPutRequestsBody.getId() );
        users.name( UsersPutRequestsBody.getName() );
        users.senha( UsersPutRequestsBody.getSenha() );
        users.email( UsersPutRequestsBody.getEmail() );
        users.cpf( UsersPutRequestsBody.getCpf() );

        return users.build();
    }
}
