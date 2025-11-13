package com.codeup.eventapp.application.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeup.eventapp.application.port.in.VenueUseCase;
import com.codeup.eventapp.application.port.out.VenuePersistencePort;
import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.exception.ConflictException;
import com.codeup.eventapp.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

/**
 * Application service implementing Venue use cases
 * Contains business logic and orchestrates domain operations
 */
@Service
@RequiredArgsConstructor
public class VenueService implements VenueUseCase {

    private final VenuePersistencePort venuePersistencePort;
    private static final String NOT_FOUND = "Venue not found";

    @Transactional
    @Override
    public Venue createVenue(Venue venue) {
        if (venuePersistencePort.existsByNameIgnoreCase(venue.getName())) {
            throw new ConflictException("Duplicated venue name");
        }
        return venuePersistencePort.save(venue);
    }

    @Transactional(readOnly = true)
    @Override
    public Venue getVenue(Long id) {
        return venuePersistencePort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Venue> listVenues(Pageable pageable) {
        return venuePersistencePort.findAll(pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Venue> listDeletedVenues(Pageable pageable) {
        return venuePersistencePort.findAllDeleted(pageable);
    }

    @Transactional
    @Override
    public Venue updateVenue(Long id, Venue venue) {
        Venue existing = venuePersistencePort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        if (!existing.getName().equalsIgnoreCase(venue.getName())
            && venuePersistencePort.existsByNameIgnoreCase(venue.getName())) {
            throw new ConflictException("Duplicated venue name");
        }

        existing.setName(venue.getName());
        existing.setAddress(venue.getAddress());
        existing.setCapacity(venue.getCapacity());

        return venuePersistencePort.save(existing);
    }

    @Transactional
    @Override
    public void deleteVenue(Long id) {
        Venue existing = venuePersistencePort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        venuePersistencePort.delete(existing);
    }

    @Transactional
    @Override
    public void restoreVenue(Long id) {
        venuePersistencePort.findDeleted(id)
            .orElseThrow(() -> new NotFoundException("Deleted venue not found"));
        venuePersistencePort.restore(id);
    }
}
