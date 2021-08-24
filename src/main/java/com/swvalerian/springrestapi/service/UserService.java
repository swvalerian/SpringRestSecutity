package com.swvalerian.springrestapi.service;

import com.swvalerian.springrestapi.model.User;
import com.swvalerian.springrestapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j // аннотация для логирования из LOMBOK
public class UserService implements BaseService<User, Long> {

    @Autowired
    UserRepository userRepository;

    @Override
    public User getId(Long id) {
        log.info("IN UserService metod getId {}", id);
        return userRepository.getOne(id);
    }

    @Override
    public List<User> getAll() {
        log.info("IN UserService metod getAll");
        return userRepository.findAll();
    }

    @Override
    public User save(User objectUser) {
        log.info("IN UserService metod save {}", objectUser);
        return userRepository.save(objectUser);
    }

    @Override
    public List<User> update(User objectUser) {
        log.info("IN UserService metod update {}", objectUser);
        userRepository.save(objectUser);
        return userRepository.findAll();
    }

    @Override
    public void deleteId(Long id) {
        log.info("IN UserService metod deleteId {}", id);
        userRepository.deleteById(id);
    }
}
