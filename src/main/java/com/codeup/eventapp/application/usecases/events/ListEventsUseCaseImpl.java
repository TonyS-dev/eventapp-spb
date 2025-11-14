package com.codeup.eventapp.application.usecases.events;

import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.domain.ports.in.events.ListEventsUseCase;
import com.codeup.eventapp.domain.ports.out.EventRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for listing all events.
 */
@Service
@Transactional(readOnly = true)
public class ListEventsUseCaseImpl implements ListEventsUseCase {

    private final EventRepositoryPort eventRepository;

    public ListEventsUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Page<Event> execute(Pageable pageable) {
        return eventRepository.findAll(pageable);
    }
}
