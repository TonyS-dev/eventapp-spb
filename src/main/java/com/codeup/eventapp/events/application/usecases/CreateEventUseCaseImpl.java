package com.codeup.eventapp.events.application.usecases;

import com.codeup.eventapp.events.domain.model.Event;
import com.codeup.eventapp.events.domain.ports.in.CreateEventUseCase;
import com.codeup.eventapp.events.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.venues.domain.ports.out.VenueRepositoryPort;
import com.codeup.eventapp.exception.ConflictException;
import com.codeup.eventapp.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Use case implementation for creating events.
 */
@Service
@Transactional
public class CreateEventUseCaseImpl implements CreateEventUseCase {

    private final EventRepositoryPort eventRepository;
    private final VenueRepositoryPort venueRepository;

    public CreateEventUseCaseImpl(EventRepositoryPort eventRepository, VenueRepositoryPort venueRepository) {
        this.eventRepository = eventRepository;
        this.venueRepository = venueRepository;
    }

    @Override
    public Event execute(Event event) {
        // Validate unique name
        if (eventRepository.existsByNameIgnoreCase(event.getName())) {
            throw new ConflictException("Duplicated event name");
        }

        // Validate venue exists if provided
        if (event.getVenue() != null && event.getVenue().getId() != null) {
            venueRepository.findById(event.getVenue().getId())
                .orElseThrow(() -> new NotFoundException("Venue not found"));
        }

        return eventRepository.save(event);
    }
}
