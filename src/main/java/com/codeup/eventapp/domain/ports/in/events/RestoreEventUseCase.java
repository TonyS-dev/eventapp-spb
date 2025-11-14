package com.codeup.eventapp.domain.ports.in.events;

/**
 * Use case for restoring a soft-deleted event.
 */
public interface RestoreEventUseCase {
    void execute(Long id);
}
