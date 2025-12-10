package com.codeup.eventapp.venues.domain.ports.in;

import com.codeup.eventapp.venues.domain.model.Venue;

/**
 * Use case for updating an existing venue.
 */
public interface UpdateVenueUseCase {
    Venue execute(Long id, Venue venue);
}
