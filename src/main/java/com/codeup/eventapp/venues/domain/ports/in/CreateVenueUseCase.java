package com.codeup.eventapp.venues.domain.ports.in;

import com.codeup.eventapp.venues.domain.model.Venue;

/**
 * Use case for creating a new venue.
 */
public interface CreateVenueUseCase {
    Venue execute(Venue venue);
}
