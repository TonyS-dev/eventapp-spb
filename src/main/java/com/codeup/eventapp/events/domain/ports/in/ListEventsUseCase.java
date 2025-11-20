package com.codeup.eventapp.events.domain.ports.in;

import com.codeup.eventapp.events.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use case for retrieving all events with pagination.
 */
public interface ListEventsUseCase {
    Page<Event> execute(Pageable pageable);
}
