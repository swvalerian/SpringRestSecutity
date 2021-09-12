package com.swvalerian.springrestapi.rest;

import com.swvalerian.springrestapi.model.Event;
import com.swvalerian.springrestapi.model.File;
import com.swvalerian.springrestapi.model.User;
import com.swvalerian.springrestapi.repository.FileRepository;
import com.swvalerian.springrestapi.service.EventService;
import com.swvalerian.springrestapi.service.FileService;
import com.swvalerian.springrestapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/files")
public class FileRestControllerV1 {

    @Autowired
    private FileService fileService;

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;


    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public ResponseEntity<List<File>> getAllFiles() {
        List<File> fileList = this.fileService.getAll();

        return new ResponseEntity<>(fileList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public ResponseEntity<File> getFileById(@PathVariable("id") Long id) {
        File file = this.fileService.getId(id);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:write') or hasAuthority('moder:write')")
    public void deleteFileById(@RequestParam("id") Long fileId) {
        LocalDateTime deleteTime = LocalDateTime.now();

        // пометим файл как удаленный
        File file = this.fileService.getId(fileId);
        file.setLocation(file.getLocation() + " DELETED");

        fileService.update(file);

        Event event = this.eventService.getAll().stream()
                .filter(f -> f.getFile().getId().equals(fileId))
                .findFirst().orElse(null);

        // а так же отметим в таблице событий, дату удаления файла
        event.setDeleted(deleteTime);

        this.eventService.update(event);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write') or hasAuthority('moder:write')")
    public ResponseEntity<File> saveFile(@RequestParam("location") String location, @RequestParam("userName") String userName) {
        LocalDateTime createTime = LocalDateTime.now();

        File newFile = fileService.save(new File(location));
        User newUser = userService.save(new User(userName));

        eventService.save(new Event(createTime,null,null,newFile,newUser));

        return new ResponseEntity<>(newFile, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<List<File>> updateFile(@RequestParam("id") Long id, @RequestParam("location") String location) {
        LocalDateTime updateTime = LocalDateTime.now();

        File updateFile = fileService.getId(id);
        updateFile.setLocation(location);

        fileService.update(updateFile);

        Event event = this.eventService.getAll().stream()
                .filter(f -> f.getFile().getId().equals(id))
                .findFirst().orElse(null);

        event.setUpdated(updateTime);

        eventService.update(event);
        return this.getAllFiles();
    }

}
