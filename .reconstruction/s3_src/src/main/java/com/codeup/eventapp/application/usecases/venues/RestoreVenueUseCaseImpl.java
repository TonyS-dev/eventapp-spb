package com.codeup.eventapp.application.usecases.venues;

import com.codeup.eventapp.domain.ports.in.venues.RestoreVenueUseCase;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for restoring soft-deleted venues.
 */
@Service
@Transactional
public class RestoreVenueUseCaseImpl implements RestoreVenueUseCase {

    private final VenueRepositoryPort venueRepository;

    public RestoreVenueUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public void execute(Long id) {
        venueRepository.findDeleted(id)
            .orElseThrow(() -> new NotFoundException("Deleted venue not found"));
        venueRepository.restore(id);
    }
}
