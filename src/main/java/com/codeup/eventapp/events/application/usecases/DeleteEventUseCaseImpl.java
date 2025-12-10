package com.codeup.eventapp.events.application.usecases;

import com.codeup.eventapp.events.domain.model.Event;
import com.codeup.eventapp.events.domain.ports.in.DeleteEventUseCase;
import com.codeup.eventapp.events.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for deleting events (soft delete).
 */
@Service
@Transactional
public class DeleteEventUseCaseImpl implements DeleteEventUseCase {

    private final EventRepositoryPort eventRepository;

    public DeleteEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void execute(Long id) {
        Event existing = eventRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Event not found"));
        eventRepository.delete(existing);
    }
}
