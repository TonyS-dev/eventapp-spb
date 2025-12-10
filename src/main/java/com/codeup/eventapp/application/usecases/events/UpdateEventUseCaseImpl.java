package com.codeup.eventapp.application.usecases.events;

import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.domain.ports.in.events.UpdateEventUseCase;
import com.codeup.eventapp.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.domain.exception.ConflictException;
import com.codeup.eventapp.domain.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for updating events.
 */
@Service
@Transactional
public class UpdateEventUseCaseImpl implements UpdateEventUseCase {

    private final EventRepositoryPort eventRepository;
    private final VenueRepositoryPort venueRepository;

    public UpdateEventUseCaseImpl(EventRepositoryPort eventRepository, VenueRepositoryPort venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Event execute(Long id, Event event) {
        Event existing = eventRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Event not found"));

        // Validate unique name if changed
        if (!existing.getName().equalsIgnoreCase(event.getName())
            && eventRepository.existsByNameIgnoreCase(event.getName())) {
            throw new ConflictException("Duplicated event name");
        }

        // Validate venue exists if provided
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            venueRepository.findById(event.getVenue().getId())
                .orElseThrow(() -> new NotFoundException("Venue not found"));
        }

        // Update fields
        existing.setName(event.getName());
        existing.setLocation(event.getLocation());
        existing.setDate(event.getDate());
        existing.setDescription(event.getDescription());
        existing.setVenue(event.getVenue());

        return eventRepository.save(existing);
    }
}
