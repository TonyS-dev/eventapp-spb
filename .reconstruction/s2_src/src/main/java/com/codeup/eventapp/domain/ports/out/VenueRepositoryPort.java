package com.codeup.eventapp.domain.ports.out;

import com.codeup.eventapp.domain.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Output port for Venue repository operations.
 * This interface defines the contract for persistence operations
 * without depending on any specific implementation.
 */
public interface VenueRepositoryPort {
    Venue save(Venue venue);
    Optional<Venue> findById(Long id);
    Page<Venue> findAll(Pageable pageable);
    Page<Venue> findAllDeleted(Pageable pageable);
    Optional<Venue> findDeleted(Long id);
    boolean existsByNameIgnoreCase(String name);
    void delete(Venue venue);
    void restore(Long id);
}
