package com.codeup.eventapp.domain.port.output;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.domain.model.Venue;

public interface VenueRepositoryPort {
    Venue save(Venue venue);
    List<Venue> findAll();
    Page<Venue> findAll(Pageable pageable);
    Page<Venue> findAllDeleted(Pageable pageable);
    Optional<Venue> findById(Long id);
    Optional<Venue> findDeletedById(Long id);
    boolean existsByName(String name);
    void deleteById(Long id);
    void restore(Long id);
}
