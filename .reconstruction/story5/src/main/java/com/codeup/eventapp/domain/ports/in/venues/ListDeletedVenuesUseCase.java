package com.codeup.eventapp.domain.ports.in.venues;

import com.codeup.eventapp.domain.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use case for retrieving deleted venues with pagination.
 */
public interface ListDeletedVenuesUseCase {
    Page<Venue> execute(Pageable pageable);
}
