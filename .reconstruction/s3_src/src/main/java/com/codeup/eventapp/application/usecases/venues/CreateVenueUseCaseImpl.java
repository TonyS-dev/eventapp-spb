package com.codeup.eventapp.application.usecases.venues;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.ports.in.venues.CreateVenueUseCase;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.domain.exception.ConflictException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for creating venues.
 */
@Service
@Transactional
public class CreateVenueUseCaseImpl implements CreateVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public CreateVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue execute(Venue venue) {
        // Validate unique name
        if (venueRepository.existsByNameIgnoreCase(venue.getName())) {
            throw new ConflictException("Duplicated venue name");
        }

        return venueRepository.save(venue);
    }
}
