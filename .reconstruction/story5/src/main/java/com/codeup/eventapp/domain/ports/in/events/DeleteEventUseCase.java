package com.codeup.eventapp.domain.ports.in.events;

/**
 * Use case for deleting an event (soft delete).
 */
public interface DeleteEventUseCase {
    void execute(Long id);
}
