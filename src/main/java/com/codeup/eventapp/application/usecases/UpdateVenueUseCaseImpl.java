package com.codeup.eventapp.application.usecases;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.ports.in.UpdateVenueUseCase;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.domain.exception.ConflictException;
import com.codeup.eventapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for updating venues.
 */
@Service
@Transactional
public class UpdateVenueUseCaseImpl implements UpdateVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public UpdateVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue execute(Long id, Venue venue) {
        Venue existing = venueRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Venue not found"));

        // Validate unique name if changed
        if (!existing.getName().equalsIgnoreCase(venue.getName())
            && venueRepository.existsByNameIgnoreCase(venue.getName())) {
            throw new ConflictException("Duplicated venue name");
        }

        // Update fields
        existing.setName(venue.getName());
        existing.setAddress(venue.getAddress());
        existing.setCapacity(venue.getCapacity());

        return venueRepository.save(existing);
    }
}
