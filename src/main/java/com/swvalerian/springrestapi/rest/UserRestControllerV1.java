package com.swvalerian.springrestapi.rest;

import com.swvalerian.springrestapi.model.User;
import com.swvalerian.springrestapi.repository.UserRepository;
import com.swvalerian.springrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users/")
public class UserRestControllerV1 {

    @Autowired
    private UserService userService;

    //описываю как я понимаю логику работы аннотаций и их параметров
    //если будет запрос GET и он будет не пустой, то ему автоматом присваивается этот метод и value задается как = id
    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //ниже мы переменной которая берется из контекста URI присваивается аргументу метода userId
    public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
        HttpHeaders httpHeaders = new HttpHeaders();

        if (userId==null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = this.userService.getId(userId);

        if (user==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = this.userService.getAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

}
