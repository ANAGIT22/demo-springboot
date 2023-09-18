package com.met.metcamp.web.demospringboot.service;

import com.met.metcamp.web.demospringboot.entities.model.Event;
import com.met.metcamp.web.demospringboot.exceptions.ApiException;
import com.met.metcamp.web.demospringboot.repository.EventRepository;
import com.met.metcamp.web.demospringboot.utils.MapperUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final MapperUtils mapperUtils;
    private final EventRepository repository;
    private final ValidationService validationService;

    public EventService(MapperUtils mapperUtils, EventRepository repository, ValidationService validationService) {
        this.mapperUtils = mapperUtils;
        this.repository = repository;
        this.validationService = validationService;
    }

    public Event createEvent(String json) {
        Event event = mapperUtils.mapToEvent(json);
        validationService.validateCreateEvent(event);
        Optional<Event> foundEvent = repository.find(event.getId());
        if (foundEvent.isPresent()) {
            throw new ApiException(400, String.format("Event %s already exists", event.getId()));
        } else {
            repository.add(event);
            return event;
        }
    }

    public List<Event> getAllEvents() {
        return repository.getEvents();
    }

    public Optional<Event> getEventById(int id) {
        return repository.find(id);
    }

    public Event updateEvent(int id, String json) {
       Optional<Event> foundEvent = repository.find(id);
       if (foundEvent.isEmpty()) {
           throw new ApiException(404, String.format("Event %s doesn't exists", id));
       }

       Event newEventData = mapperUtils.mapToEvent(json);
       validationService.validateUpdateEvent(newEventData);
       repository.update(id, newEventData);
       return newEventData;
    }

    public void deleteEvent(int id) {
        repository.find(id).ifPresent(event -> repository.delete(event.getId()));
    }
}
