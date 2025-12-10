package com.codeup.eventapp.events.domain.ports.in;

import com.codeup.eventapp.events.domain.model.Event;

/**
 * Use case for retrieving an event by ID.
 */
public interface GetEventUseCase {
    Event execute(Long id);
}
