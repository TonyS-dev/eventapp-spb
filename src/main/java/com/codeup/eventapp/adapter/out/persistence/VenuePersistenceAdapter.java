package com.codeup.eventapp.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.codeup.eventapp.application.port.out.VenuePersistencePort;
import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.entity.VenueEntity;
import com.codeup.eventapp.repository.IVenueRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Adapter that implements VenuePersistencePort using JPA
 * Translates between domain models and JPA entities
 */
@Component
@RequiredArgsConstructor
public class VenuePersistenceAdapter implements VenuePersistencePort {

    private final IVenueRepository venueRepository;
    private final VenueMapper venueMapper;

    @Override
    public Venue save(Venue venue) {
        VenueEntity entity = venueMapper.toEntity(venue);
        VenueEntity saved = venueRepository.save(entity);
        return venueMapper.toDomain(saved);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return venueRepository.findById(id)
            .map(venueMapper::toDomain);
    }

    @Override
    public Page<Venue> findAll(Pageable pageable) {
        return venueRepository.findAll(pageable)
            .map(venueMapper::toDomain);
    }

    @Override
    public Page<Venue> findAllDeleted(Pageable pageable) {
        return venueRepository.findAllDeleted(pageable)
            .map(venueMapper::toDomain);
    }

    @Override
    public Optional<Venue> findDeleted(Long id) {
        return venueRepository.findDeleted(id)
            .map(venueMapper::toDomain);
    }

    @Override
    public void delete(Venue venue) {
        VenueEntity entity = venueMapper.toEntity(venue);
        venueRepository.delete(entity);
    }

    @Override
    public void restore(Long id) {
        venueRepository.restore(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return venueRepository.existsByNameIgnoreCase(name);
    }
}
