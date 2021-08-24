package com.swvalerian.springrestapi.rest;

import com.swvalerian.springrestapi.model.Event;
import com.swvalerian.springrestapi.model.File;
import com.swvalerian.springrestapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/events")
public class EventRestControllerV1 {
    // необходимо будет использовать в методах создания, удаления и изменения
    // LocalDateTime pCreated = LocalDateTime.now();

    // далее так же изменить другие рест контроллеры, не удалять - а на месте ссылки на файл - надпись DELETED

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> eventList = this.eventService.getAll();

        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
        Event event = this.eventService.getId(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping
    public void deleteEventById(@RequestParam("id") Long eventId) {
        this.eventService.deleteId(eventId);
    }

    @PostMapping
    public ResponseEntity<Event> saveEvent(@ModelAttribute("eventModel") Event event) {
        this.eventService.save(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<List<Event>> updateEvent(@ModelAttribute("eventModel") Event event) {
        this.eventService.update(event);
        return this.getAllEvents();
    }

}
