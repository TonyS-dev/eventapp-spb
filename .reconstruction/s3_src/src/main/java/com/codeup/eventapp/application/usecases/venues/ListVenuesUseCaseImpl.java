package com.codeup.eventapp.application.usecases.venues;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.ports.in.venues.ListVenuesUseCase;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for listing all venues.
 */
@Service
@Transactional(readOnly = true)
public class ListVenuesUseCaseImpl implements ListVenuesUseCase {

    private final VenueRepositoryPort venueRepository;

    public ListVenuesUseCaseImpl(VenueRepositoryPort venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Page<Venue> execute(Pageable pageable) {
        return venueRepository.findAll(pageable);
    }
}
