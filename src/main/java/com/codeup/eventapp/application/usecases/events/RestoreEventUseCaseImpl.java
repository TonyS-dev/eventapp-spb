package com.codeup.eventapp.application.usecases.events;

import com.codeup.eventapp.domain.ports.in.events.RestoreEventUseCase;
import com.codeup.eventapp.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for restoring soft-deleted events.
 */
@Service
@Transactional
public class RestoreEventUseCaseImpl implements RestoreEventUseCase {

    private final EventRepositoryPort eventRepository;

    public RestoreEventUseCaseImpl(EventRepositoryPort eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public void execute(Long id) {
        eventRepository.findDeleted(id)
            .orElseThrow(() -> new NotFoundException("Deleted event not found"));
        eventRepository.restore(id);
    }
}
