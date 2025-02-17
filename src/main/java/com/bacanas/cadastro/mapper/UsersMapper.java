package com.bacanas.cadastro.mapper;


import com.bacanas.cadastro.domain.Users;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class UsersMapper {
    public static final UsersMapper INSTANCE = Mappers.getMapper(UsersMapper.class);

    public abstract Users toUsers(UsersPostRequestsBody usersPostRequestsBody);

    public abstract Users toUsers(UsersPutRequestsBody UsersPutRequestsBody);

}
