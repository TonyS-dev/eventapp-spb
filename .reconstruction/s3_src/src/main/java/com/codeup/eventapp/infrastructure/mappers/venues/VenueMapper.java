package com.codeup.eventapp.infrastructure.mappers.venues;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.infrastructure.entities.VenueEntity;
import org.springframework.stereotype.Component;

/**
 * Mapper between Venue domain model and VenueEntity.
 */
@Component
public class VenueMapper {

    public Venue toDomain(VenueEntity entity) {
        if (entity == null) {
            return null;
        }
        
        Venue venue = new Venue();
        venue.setId(entity.getId());
        venue.setName(entity.getName());
        venue.setAddress(entity.getAddress());
        venue.setCapacity(entity.getCapacity());
        
        return venue;
    }

    public VenueEntity toEntity(Venue domain) {
        if (domain == null) {
            return null;
        }
        
        return VenueEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .address(domain.getAddress())
                .capacity(domain.getCapacity())
                .build();
    }
}
