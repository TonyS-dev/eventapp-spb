package com.codeup.eventapp.events.domain.ports.out;

import com.codeup.eventapp.events.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Output port for Event repository operations.
 * This interface defines the contract for persistence operations
 * without depending on any specific implementation.
 */
public interface EventRepositoryPort {
    Event save(Event event);
    Optional<Event> findById(Long id);
    Page<Event> findAll(Pageable pageable);
    Page<Event> findAllDeleted(Pageable pageable);
    Optional<Event> findDeleted(Long id);
    boolean existsByNameIgnoreCase(String name);
    void delete(Event event);
    void restore(Long id);
}
