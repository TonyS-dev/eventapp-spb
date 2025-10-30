package com.codeup.eventapp.domain.ports.in.events;

import com.codeup.eventapp.domain.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use case for retrieving deleted events with pagination.
 */
public interface ListDeletedEventsUseCase {
    Page<Event> execute(Pageable pageable);
}
