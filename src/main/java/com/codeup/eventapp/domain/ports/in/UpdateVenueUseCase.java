package com.codeup.eventapp.domain.ports.in;

import com.codeup.eventapp.domain.model.Venue;

/**
 * Use case for updating an existing venue.
 */
public interface UpdateVenueUseCase {
    Venue execute(Long id, Venue venue);
}
