package com.codeup.eventapp.domain.ports.in.events;

import com.codeup.eventapp.domain.model.Event;

/**
 * Use case for creating a new event.
 */
public interface CreateEventUseCase {
    Event execute(Event event);
}
