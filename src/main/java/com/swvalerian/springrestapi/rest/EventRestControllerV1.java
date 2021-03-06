package com.swvalerian.springrestapi.rest;

import com.swvalerian.springrestapi.model.Event;
import com.swvalerian.springrestapi.model.File;
import com.swvalerian.springrestapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
public class EventRestControllerV1 {

    @Autowired
    private EventService eventService;

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> eventList = this.eventService.getAll();

        return new ResponseEntity<>(eventList, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('admin:read') or hasAuthority('user:read')")
    public ResponseEntity<Event> getEventById(@PathVariable("id") Long id) {
        Event event = this.eventService.getId(id);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public void deleteEventById(@RequestParam("id") Long eventId) {
        this.eventService.deleteId(eventId);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<Event> saveEvent(@ModelAttribute("eventModel") Event event) {
        this.eventService.save(event);
        return new ResponseEntity<>(event, HttpStatus.OK);
    }

    @PutMapping
    @PreAuthorize("hasAuthority('admin:write')")
    public ResponseEntity<List<Event>> updateEvent(@ModelAttribute("eventModel") Event event) {
        this.eventService.update(event);
        return this.getAllEvents();
    }

}
