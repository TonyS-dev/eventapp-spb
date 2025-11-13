package com.codeup.eventapp.adapter.out.persistence;

import org.springframework.stereotype.Component;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.entity.VenueEntity;

/**
 * Mapper to convert between Venue domain model and VenueEntity
 */
@Component
public class VenueMapper {

    public VenueEntity toEntity(Venue venue) {
        if (venue == null) {
            return null;
        }
        
        return VenueEntity.builder()
            .id(venue.getId())
            .name(venue.getName())
            .address(venue.getAddress())
            .capacity(venue.getCapacity())
            .build();
    }

    public Venue toDomain(VenueEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new Venue(
            entity.getId(),
            entity.getName(),
            entity.getAddress(),
            entity.getCapacity()
        );
    }
}
