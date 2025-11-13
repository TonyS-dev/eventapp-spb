package com.codeup.eventapp.application.port.out;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.domain.model.Venue;

import java.util.Optional;

/**
 * Output port for Venue persistence
 * Defines the contract for venue data access without framework dependencies
 */
public interface VenuePersistencePort {
    Venue save(Venue venue);
    Optional<Venue> findById(Long id);
    Page<Venue> findAll(Pageable pageable);
    Page<Venue> findAllDeleted(Pageable pageable);
    Optional<Venue> findDeleted(Long id);
    void delete(Venue venue);
    void restore(Long id);
    boolean existsByNameIgnoreCase(String name);
}
