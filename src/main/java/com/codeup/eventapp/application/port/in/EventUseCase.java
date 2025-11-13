package com.codeup.eventapp.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.domain.model.Event;

/**
 * Input port for Event use cases
 * Defines the business operations that can be performed on events
 */
public interface EventUseCase {
    Event createEvent(Event event);
    Event getEvent(Long id);
    Page<Event> listEvents(Pageable pageable);
    Page<Event> listDeletedEvents(Pageable pageable);
    Event updateEvent(Long id, Event event);
    void deleteEvent(Long id);
    void restoreEvent(Long id);
}
