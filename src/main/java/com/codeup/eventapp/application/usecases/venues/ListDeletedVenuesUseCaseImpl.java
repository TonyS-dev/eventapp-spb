package com.codeup.eventapp.application.usecases.venues;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.ports.in.venues.ListDeletedVenuesUseCase;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for listing deleted venues.
 */
@Service
@Transactional(readOnly = true)
public class ListDeletedVenuesUseCaseImpl implements ListDeletedVenuesUseCase {

    private final VenueRepositoryPort venueRepository;

    public ListDeletedVenuesUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Page<Venue> execute(Pageable pageable) {
        return venueRepository.findAllDeleted(pageable);
    }
}
