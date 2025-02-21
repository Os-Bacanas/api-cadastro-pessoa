package com.bacanas.cadastro.service;

import com.bacanas.cadastro.domain.User;
import com.bacanas.cadastro.exceptions.BadRequestException;
import com.bacanas.cadastro.mapper.UsersMapper;
import com.bacanas.cadastro.repository.UsersRepository;
import com.bacanas.cadastro.requests.UsersPostRequestsBody;
import com.bacanas.cadastro.requests.UsersPutRequestsBody;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public List<User> listAll() {
        return usersRepository.findAll();
    }

    public List<User> findByName(String name) {
        return usersRepository.findByName(name);
    }

    public User findByIdOrThrowBadException(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new BadRequestException("User not found"));
    }

    public User save(UsersPostRequestsBody usersPostRequestsBody) {
        //Anime anime = Anime.builder().name(animePostRequestsBody.getName()).build();
        return usersRepository.save(UsersMapper.INSTANCE.toUsers(usersPostRequestsBody));
    }

    public void delete(long id) {
        usersRepository.delete(findByIdOrThrowBadException(id));
    }

    public void replace(UsersPutRequestsBody usersPutRequestsBody) {
        User savedUser = findByIdOrThrowBadException(usersPutRequestsBody.getId());
        User user = UsersMapper.INSTANCE.toUsers(usersPutRequestsBody);
        user.setId(savedUser.getId());
        usersRepository.save(user);
    }
}