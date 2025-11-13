package com.codeup.eventapp.domain.ports.in;

/**
 * Use case for deleting a venue (soft delete).
 */
public interface DeleteVenueUseCase {
    void execute(Long id);
}
