package com.codeup.eventapp.application.usecases;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.ports.in.GetVenueUseCase;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for retrieving venues by ID.
 */
@Service
@Transactional(readOnly = true)
public class GetVenueUseCaseImpl implements GetVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public GetVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Venue execute(Long id) {
        return venueRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Venue not found"));
    }
}
