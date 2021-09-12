package com.swvalerian.springrestapi.rest;

import com.swvalerian.springrestapi.model.User;
import com.swvalerian.springrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/users")
public class UserRestControllerV2 {

    @Autowired
    private UserService userService;


    @RequestMapping(value = "{userId}", method = RequestMethod.GET)
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
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
    @PreAuthorize("hasAuthority('admin:read')")
    public ResponseEntity<List<User>> getAllUser() {
        List<User> userList = this.userService.getAll();
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<User> saveUser(@ModelAttribute("userModel") User user) {
        this.userService.save(user);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<List<User>> updateUser(@ModelAttribute("userModel") User user) {
        this.userService.update(user);
        List<User> userList = this.userService.getAll();
        return new ResponseEntity<>(userList, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public void deleteUser(@PathVariable("id") Long userId) {
        User user = userService.getId(userId);
        user.setName(user.getName() + "DELETED");
        userService.update(user);
    }

}