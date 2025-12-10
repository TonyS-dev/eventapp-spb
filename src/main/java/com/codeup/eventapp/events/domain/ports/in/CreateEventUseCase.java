package com.codeup.eventapp.events.domain.ports.in;

import com.codeup.eventapp.events.domain.model.Event;

/**
 * Use case for creating a new event.
 */
public interface CreateEventUseCase {
    Event execute(Event event);
}
