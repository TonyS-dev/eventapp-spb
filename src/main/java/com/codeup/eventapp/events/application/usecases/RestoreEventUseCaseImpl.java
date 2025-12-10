package com.codeup.eventapp.events.application.usecases;

import com.codeup.eventapp.events.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.events.domain.ports.in.RestoreEventUseCase;
import com.codeup.eventapp.exception.NotFoundException;
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
