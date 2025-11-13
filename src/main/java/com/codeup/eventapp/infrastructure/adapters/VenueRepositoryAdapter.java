package com.codeup.eventapp.infrastructure.adapters;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.infrastructure.entities.VenueEntity;
import com.codeup.eventapp.infrastructure.mappers.VenueMapper;
import com.codeup.eventapp.infrastructure.repositories.JpaVenueRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adapter that implements the VenueRepositoryPort using JPA.
 * This connects the domain layer to the infrastructure layer.
 */
@Component
public class VenueRepositoryAdapter implements VenueRepositoryPort {

    private final JpaVenueRepository jpaRepository;
    private final VenueMapper mapper;

    public VenueRepositoryAdapter(JpaVenueRepository jpaRepository, VenueMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Venue save(Venue venue) {
        VenueEntity entity = mapper.toEntity(venue);
        VenueEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Venue> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Venue> findAllDeleted(Pageable pageable) {
        return jpaRepository.findAllDeleted(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Venue> findDeleted(Long id) {
        return jpaRepository.findDeleted(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return jpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public void delete(Venue venue) {
        VenueEntity entity = mapper.toEntity(venue);
        jpaRepository.delete(entity);
    }

    @Override
    public void restore(Long id) {
        jpaRepository.restore(id);
    }
}
