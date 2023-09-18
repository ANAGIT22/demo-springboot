package com.met.metcamp.web.demospringboot.controllers;

import com.met.metcamp.web.demospringboot.entities.model.Event;
import com.met.metcamp.web.demospringboot.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/met/metcamp/web/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getAllEvents() {
        return ResponseEntity.ok(Map.of("events", eventService.getAllEvents()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Event>> getEventById(@PathVariable int id) {
        Optional<Event> event = eventService.getEventById(id);
        return event.map(value -> ResponseEntity.ok(Map.of("event", value)))
                    .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createEvent(@RequestBody String body) {
        return ResponseEntity.status(201).body(Map.of("created_event", eventService.createEvent(body)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, Event>> updateEvent(@PathVariable int id,
                                                           @RequestBody String body) {
        return ResponseEntity.ok(Map.of("updated_data", eventService.updateEvent(id, body)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteEvent(@PathVariable int id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

}
