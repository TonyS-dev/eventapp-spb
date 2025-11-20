package com.codeup.eventapp.venues.domain.ports.in;

/**
 * Use case for deleting a venue (soft delete).
 */
public interface DeleteVenueUseCase {
    void execute(Long id);
}
