package com.codeup.eventapp.venues.domain.ports.in;

import com.codeup.eventapp.venues.domain.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use case for retrieving deleted venues with pagination.
 */
public interface ListDeletedVenuesUseCase {
    Page<Venue> execute(Pageable pageable);
}
