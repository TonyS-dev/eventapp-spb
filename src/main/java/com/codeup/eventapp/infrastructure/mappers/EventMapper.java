package com.codeup.eventapp.infrastructure.mappers;

import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.infrastructure.entities.EventEntity;
import com.codeup.eventapp.infrastructure.entities.VenueEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between Event domain model and EventEntity.
 */
@Component
public class EventMapper {

    private final VenueMapper venueMapper;

    public EventMapper(VenueMapper venueMapper) {
        this.venueMapper = venueMapper;
    }

    public Event toDomain(EventEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Event event = new Event();
        event.setId(entity.getId());
        event.setName(entity.getName());
        event.setLocation(entity.getLocation());
        event.setDate(entity.getDate());
        event.setDescription(entity.getDescription());
        
        if (entity.getVenue() != null) {
            event.setVenue(venueMapper.toDomain(entity.getVenue()));
        }
        
        return event;
    }

    public EventEntity toEntity(Event domain) {
        if (domain == null) {
            return null;
        }
        
        VenueEntity venueEntity = null;
        if (domain.getVenue() != null) {
            venueEntity = venueMapper.toEntity(domain.getVenue());
        }
        
        return EventEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .location(domain.getLocation())
                .date(domain.getDate())
                .description(domain.getDescription())
                .venue(venueEntity)
                .build();
    }
}
