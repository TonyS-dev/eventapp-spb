package com.codeup.eventapp.domain.ports.in;

import com.codeup.eventapp.domain.model.Event;

/**
 * Use case for updating an existing event.
 */
public interface UpdateEventUseCase {
    Event execute(Long id, Event event);
}
