package com.codeup.eventapp.application.usecases.events;

import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.domain.ports.in.events.ListDeletedEventsUseCase;
import com.codeup.eventapp.domain.ports.out.EventRepositoryPort;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for listing deleted events.
 */
@Service
@Transactional(readOnly = true)
public class ListDeletedEventsUseCaseImpl implements ListDeletedEventsUseCase {

    private final EventRepositoryPort eventRepository;

    public ListDeletedEventsUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Page<Event> execute(Pageable pageable) {
        return eventRepository.findAllDeleted(pageable);
    }
}
