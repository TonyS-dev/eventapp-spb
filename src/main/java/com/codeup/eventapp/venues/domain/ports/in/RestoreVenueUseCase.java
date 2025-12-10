package com.codeup.eventapp.venues.domain.ports.in;

/**
 * Use case for restoring a soft-deleted venue.
 */
public interface RestoreVenueUseCase {
    void execute(Long id);
}
