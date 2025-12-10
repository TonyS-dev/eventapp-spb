package com.codeup.eventapp.venues.application.usecases;

import com.codeup.eventapp.venues.domain.model.Venue;
import com.codeup.eventapp.venues.domain.ports.in.ListDeletedVenuesUseCase;
import com.codeup.eventapp.venues.domain.ports.out.VenueRepositoryPort;
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
