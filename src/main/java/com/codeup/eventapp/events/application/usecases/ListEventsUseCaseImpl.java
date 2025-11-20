package com.codeup.eventapp.events.application.usecases;

import com.codeup.eventapp.events.domain.model.Event;
import com.codeup.eventapp.events.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.events.domain.ports.in.ListEventsUseCase;
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
