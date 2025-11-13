package com.codeup.eventapp.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.entity.EventEntity;
import com.codeup.eventapp.entity.VenueEntity;

import lombok.RequiredArgsConstructor;

/**
 * Mapper to convert between Event domain model and EventEntity
 */
@Component
@RequiredArgsConstructor
public class EventMapper {

    private final VenueMapper venueMapper;

    public EventEntity toEntity(Event event) {
        if (event == null) {
            return null;
        }
        
        VenueEntity venueEntity = null;
        if (event.getVenue() != null) {
            venueEntity = venueMapper.toEntity(event.getVenue());
        }
        
        return EventEntity.builder()
            .id(event.getId())
            .name(event.getName())
            .location(event.getLocation())
            .date(event.getDate())
            .description(event.getDescription())
            .venue(venueEntity)
            .build();
    }

    public Event toDomain(EventEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new Event(
            entity.getId(),
            entity.getName(),
            entity.getLocation(),
            entity.getDate(),
            entity.getDescription(),
            venueMapper.toDomain(entity.getVenue())
        );
    }
}
