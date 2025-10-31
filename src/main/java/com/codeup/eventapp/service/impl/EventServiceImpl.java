package com.codeup.eventapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeup.eventapp.entity.EventEntity;
import com.codeup.eventapp.entity.VenueEntity;
import com.codeup.eventapp.exception.ConflictException;
import com.codeup.eventapp.exception.NotFoundException;
import com.codeup.eventapp.repository.IEventRepository;
import com.codeup.eventapp.service.IEventService;
import com.codeup.eventapp.service.IVenueService;
import com.codeup.eventapp.web.dto.event.EventRequest;
import com.codeup.eventapp.web.dto.event.EventResponse;
import com.codeup.eventapp.web.dto.venue.VenueResponse;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final IEventRepository repo;
    private final IVenueService venueService;
    private static final String NOT_FOUND = "Event not found";

    @Transactional
    @Override
    public EventResponse create(EventRequest req) {
        if (repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Duplicated event name");
        }
        
        VenueEntity venue = null;
        if (req.venueId() != null) {
            venueService.get(req.venueId()); // Validate venue exists if provided
            venue = VenueEntity.builder().id(req.venueId()).build();
        }
        
        EventEntity saved = repo.save(EventEntity.builder()
                .name(req.name())
                .location(req.location())
                .date(req.date())
                .description(req.description())
                .venue(venue)
                .build()
        );

        VenueResponse venueResponse = null;
        if (venue != null) {
            venueResponse = venueService.get(venue.getId());
        }

        return new EventResponse(saved.getId(), saved.getName(), saved.getLocation(), saved.getDate(), saved.getDescription(), venueResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public EventResponse get(Long id) {
        EventEntity event = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        
        VenueResponse venue = null;
        if (event.getVenue() != null) {
            venue = venueService.get(event.getVenue().getId());
        }
        
        return new EventResponse(event.getId(), event.getName(), event.getLocation(), event.getDate(), event.getDescription(), venue);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EventResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
            .map(u -> {
                VenueResponse venue = null;
                if (u.getVenue() != null) {
                    venue = venueService.get(u.getVenue().getId());
                }
                return new EventResponse(u.getId(), u.getName(), u.getLocation(), u.getDate(), u.getDescription(), venue);
            });
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EventResponse> listDeleted(Pageable pageable) {
        return repo.findAllDeleted(pageable)
            .map(u -> {
                VenueResponse venue = null;
                if (u.getVenue() != null) {
                    venue = venueService.get(u.getVenue().getId());
                }
                return new EventResponse(u.getId(), u.getName(), u.getLocation(), u.getDate(), u.getDescription(), venue);
            });
    }

    @Transactional
    @Override
    public EventResponse update(Long id, EventRequest req) {
        EventEntity existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

    if (!existing.getName().equalsIgnoreCase(req.name())
        && repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Duplicated event name");
        }

        existing.setName(req.name());
        existing.setLocation(req.location());
        existing.setDate(req.date());
        existing.setDescription(req.description());
        if (req.venueId() != null) {
            // Validate that the venue exists
            venueService.get(req.venueId());
            existing.setVenue(VenueEntity.builder().id(req.venueId()).build());
        } else {
            existing.setVenue(null);
        }

        EventEntity updated = repo.save(existing);
        
        VenueResponse venue = null;
        if (updated.getVenue() != null) {
            venue = venueService.get(updated.getVenue().getId());
        }

        return new EventResponse(updated.getId(), updated.getName(), updated.getLocation(), updated.getDate(), updated.getDescription(), venue);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        EventEntity existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        repo.delete(existing);
    }

    @Transactional
    @Override
    public void restore(Long id) {
        repo.findDeleted(id)
            .orElseThrow(() -> new NotFoundException("Deleted event not found"));
        repo.restore(id);
    }
}