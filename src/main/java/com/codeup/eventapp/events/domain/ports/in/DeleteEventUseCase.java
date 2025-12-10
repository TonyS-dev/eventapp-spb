package com.codeup.eventapp.events.domain.ports.in;

/**
 * Use case for deleting an event (soft delete).
 */
public interface DeleteEventUseCase {
    void execute(Long id);
}
