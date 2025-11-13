package com.codeup.eventapp.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.domain.model.Event;

import java.util.Optional;

/**
 * Output port for Event persistence
 * Defines the contract for event data access without framework dependencies
 */
public interface EventPersistencePort {
    Event save(Event event);
    Optional<Event> findById(Long id);
    Page<Event> findAll(Pageable pageable);
    Page<Event> findAllDeleted(Pageable pageable);
    Optional<Event> findDeleted(Long id);
    void delete(Event event);
    void restore(Long id);
    boolean existsByNameIgnoreCase(String name);
}
