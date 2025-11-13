package com.codeup.eventapp.domain.ports.in;

import com.codeup.eventapp.domain.model.Venue;

/**
 * Use case for creating a new venue.
 */
public interface CreateVenueUseCase {
    Venue execute(Venue venue);
}
