package com.codeup.eventapp.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeup.eventapp.application.port.in.EventUseCase;
import com.codeup.eventapp.application.port.out.EventPersistencePort;
import com.codeup.eventapp.application.port.out.VenuePersistencePort;
import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.exception.ConflictException;
import com.codeup.eventapp.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Application service implementing Event use cases
 * Contains business logic and orchestrates domain operations
 */
@Service
@RequiredArgsConstructor
public class EventService implements EventUseCase {

    private final EventPersistencePort eventPersistencePort;
    private final VenuePersistencePort venuePersistencePort;
    private static final String NOT_FOUND = "Event not found";

    @Transactional
    @Override
    public Event createEvent(Event event) {
        if (eventPersistencePort.existsByNameIgnoreCase(event.getName())) {
            throw new ConflictException("Duplicated event name");
        }
        
        // Validate venue exists if provided
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            Venue venue = venuePersistencePort.findById(event.getVenue().getId())
                .orElseThrow(() -> new NotFoundException("Venue not found"));
            event.setVenue(venue);
        }
        
        return eventPersistencePort.save(event);
    }

    @Transactional(readOnly = true)
    @Override
    public Event getEvent(Long id) {
        return eventPersistencePort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Event> listEvents(Pageable pageable) {
        return eventPersistencePort.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Event> listDeletedEvents(Pageable pageable) {
        return eventPersistencePort.findAllDeleted(pageable);
    }

    @Transactional
    @Override
    public Event updateEvent(Long id, Event event) {
        Event existing = eventPersistencePort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        if (!existing.getName().equalsIgnoreCase(event.getName())
            && eventPersistencePort.existsByNameIgnoreCase(event.getName())) {
            throw new ConflictException("Duplicated event name");
        }

        existing.setName(event.getName());
        existing.setLocation(event.getLocation());
        existing.setDate(event.getDate());
        existing.setDescription(event.getDescription());
        
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            Venue venue = venuePersistencePort.findById(event.getVenue().getId())
                .orElseThrow(() -> new NotFoundException("Venue not found"));
            existing.setVenue(venue);
        } else {
            existing.setVenue(null);
        }

        return eventPersistencePort.save(existing);
    }

    @Transactional
    @Override
    public void deleteEvent(Long id) {
        Event existing = eventPersistencePort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        eventPersistencePort.delete(existing);
    }

    @Transactional
    @Override
    public void restoreEvent(Long id) {
        eventPersistencePort.findDeleted(id)
            .orElseThrow(() -> new NotFoundException("Deleted event not found"));
        eventPersistencePort.restore(id);
    }
}
