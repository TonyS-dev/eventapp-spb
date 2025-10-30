package com.codeup.eventapp.domain.ports.in.venues;

import com.codeup.eventapp.domain.model.Venue;

/**
 * Use case for retrieving a venue by ID.
 */
public interface GetVenueUseCase {
    Venue execute(Long id);
}
