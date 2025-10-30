package com.codeup.eventapp.domain.ports.in;

/**
 * Use case for restoring a soft-deleted event.
 */
public interface RestoreEventUseCase {
    void execute(Long id);
}
