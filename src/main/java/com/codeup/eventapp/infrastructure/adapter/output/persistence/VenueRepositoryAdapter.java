package com.codeup.eventapp.infrastructure.adapter.output.persistence;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.port.output.VenueRepositoryPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class VenueRepositoryAdapter implements VenueRepositoryPort {

    private final VenueJpaRepository jpaRepository;

    @Override
    public Venue save(Venue venue) {
        VenueEntity entity = toEntity(venue);
        VenueEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public List<Venue> findAll() {
        return jpaRepository.findAll().stream()
            .map(this::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public Page<Venue> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
            .map(this::toDomain);
    }

    @Override
    public Page<Venue> findAllDeleted(Pageable pageable) {
        return jpaRepository.findAllDeleted(pageable)
            .map(this::toDomain);
    }

    @Override
    public Optional<Venue> findById(Long id) {
        return jpaRepository.findById(id)
            .map(this::toDomain);
    }

    @Override
    public Optional<Venue> findDeletedById(Long id) {
        return jpaRepository.findDeleted(id)
            .map(this::toDomain);
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public void restore(Long id) {
        jpaRepository.restore(id);
    }

    // Mapper methods
    private Venue toDomain(VenueEntity entity) {
        return new Venue(
            entity.getId(),
            entity.getName(),
            entity.getAddress(),
            entity.getCapacity()
        );
    }

    private VenueEntity toEntity(Venue venue) {
        return VenueEntity.builder()
            .id(venue.getId())
            .name(venue.getName())
            .address(venue.getAddress())
            .capacity(venue.getCapacity())
            .build();
    }
}
