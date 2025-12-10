package com.codeup.eventapp.venues.domain.ports.in;

import com.codeup.eventapp.venues.domain.model.Venue;

/**
 * Use case for retrieving a venue by ID.
 */
public interface GetVenueUseCase {
    Venue execute(Long id);
}
