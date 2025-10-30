package com.codeup.eventapp.application.usecases.events;

import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.domain.ports.in.events.GetEventUseCase;
import com.codeup.eventapp.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for retrieving events by ID.
 */
@Service
@Transactional(readOnly = true)
public class GetEventUseCaseImpl implements GetEventUseCase {

    private final EventRepositoryPort eventRepository;

    public GetEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public Event execute(Long id) {
        return eventRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Event not found"));
    }
}
