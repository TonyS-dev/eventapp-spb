package com.codeup.eventapp.domain.ports.in;

import com.codeup.eventapp.domain.model.Event;

/**
 * Use case for retrieving an event by ID.
 */
public interface GetEventUseCase {
    Event execute(Long id);
}
