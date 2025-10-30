package com.codeup.eventapp.application.usecases.venues;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.ports.in.venues.DeleteVenueUseCase;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for deleting venues (soft delete).
 */
@Service
@Transactional
public class DeleteVenueUseCaseImpl implements DeleteVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public DeleteVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public void execute(Long id) {
        Venue existing = venueRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Venue not found"));
        venueRepository.delete(existing);
    }
}
