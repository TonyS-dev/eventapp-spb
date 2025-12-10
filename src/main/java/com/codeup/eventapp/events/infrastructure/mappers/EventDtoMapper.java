package com.codeup.eventapp.events.infrastructure.mappers;

import com.codeup.eventapp.events.domain.model.Event;
import com.codeup.eventapp.venues.domain.model.Venue;
import com.codeup.eventapp.events.infrastructure.dto.EventRequest;
import com.codeup.eventapp.events.infrastructure.dto.EventResponse;
import com.codeup.eventapp.venues.infrastructure.dto.VenueResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper between Event domain model and DTOs (Request/Response).
 */
@Component
public class EventDtoMapper {

    public Event requestToDomain(EventRequest request) {
        if (request == null) {
            return null;
        }
        
        Event event = new Event();
        event.setName(request.name());
        event.setLocation(request.location());
        event.setDate(request.date());
        event.setDescription(request.description());
        
        // Set venue reference if venueId is provided
        if (request.venueId() != null) {
            Venue venue = new Venue();
            venue.setId(request.venueId());
            event.setVenue(venue);
        }
        
        return event;
    }

    public EventResponse domainToResponse(Event event) {
        if (event == null) {
            return null;
        }
        
        VenueResponse venueResponse = null;
        if (event.getVenue() != null) {
            venueResponse = new VenueResponse(
                event.getVenue().getId(),
                event.getVenue().getName(),
                event.getVenue().getAddress(),
                event.getVenue().getCapacity()
            );
        }
        
        return new EventResponse(
            event.getId(),
            event.getName(),
            event.getLocation(),
            event.getDate(),
            event.getDescription(),
            venueResponse
        );
    }
}
