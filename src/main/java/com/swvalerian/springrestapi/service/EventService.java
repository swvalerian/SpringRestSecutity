package com.swvalerian.springrestapi.service;

import com.swvalerian.springrestapi.model.Event;
import com.swvalerian.springrestapi.repository.EventRepository;
import org.hibernate.type.LocalDateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService implements BaseService<Event, Long>{
    @Autowired
    private EventRepository eventRepository;

    @Override
    public Event getId(Long eventId) {
        return this.eventRepository.getOne(eventId);
    }

    @Override
    public List<Event> getAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public Event save(Event event) {
        return this.eventRepository.save(event);
    }

    @Override
    public List<Event> update(Event event) {
        this.eventRepository.save(event);
        return this.eventRepository.findAll();
    }

    @Override
    public void deleteId(Long eventId) {
        this.eventRepository.deleteById(eventId);
    }
}
