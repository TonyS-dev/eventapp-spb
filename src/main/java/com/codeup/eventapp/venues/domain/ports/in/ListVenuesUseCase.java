package com.codeup.eventapp.venues.domain.ports.in;

import com.codeup.eventapp.venues.domain.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use case for retrieving all venues with pagination.
 */
public interface ListVenuesUseCase {
    Page<Venue> execute(Pageable pageable);
}
