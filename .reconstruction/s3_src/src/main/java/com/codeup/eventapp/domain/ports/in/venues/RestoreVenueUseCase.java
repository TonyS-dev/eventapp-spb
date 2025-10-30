package com.codeup.eventapp.domain.ports.in.venues;

/**
 * Use case for restoring a soft-deleted venue.
 */
public interface RestoreVenueUseCase {
    void execute(Long id);
}
