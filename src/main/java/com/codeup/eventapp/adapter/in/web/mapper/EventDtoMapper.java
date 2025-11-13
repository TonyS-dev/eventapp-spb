package com.codeup.eventapp.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.codeup.eventapp.adapter.in.web.dto.event.EventRequest;
import com.codeup.eventapp.adapter.in.web.dto.event.EventResponse;
import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.domain.model.Venue;

import lombok.RequiredArgsConstructor;

/**
 * Mapper to convert between Event domain model and web DTOs
 */
@Component
@RequiredArgsConstructor
public class EventDtoMapper {

    private final VenueDtoMapper venueDtoMapper;

    public Event toDomain(EventRequest request) {
        if (request == null) {
            return null;
        }
        
        Venue venue = null;
        if (request.venueId() != null) {
            venue = new Venue(request.venueId(), null, null, 0);
        }
        
        return new Event(
            null,
            request.name(),
            request.location(),
            request.date(),
            request.description(),
            venue
        );
    }

    public EventResponse toResponse(Event event) {
        if (event == null) {
            return null;
        }
        return new EventResponse(
            event.getId(),
            event.getName(),
            event.getLocation(),
            event.getDate(),
            event.getDescription(),
            venueDtoMapper.toResponse(event.getVenue())
        );
    }
}
