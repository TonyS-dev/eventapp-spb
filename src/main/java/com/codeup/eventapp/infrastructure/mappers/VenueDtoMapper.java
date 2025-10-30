package com.codeup.eventapp.infrastructure.mappers;

import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.infrastructure.web.dto.venue.VenueRequest;
import com.codeup.eventapp.infrastructure.web.dto.venue.VenueResponse;
import org.springframework.stereotype.Component;

/**
 * Mapper between Venue domain model and DTOs (Request/Response).
 */
@Component
public class VenueDtoMapper {

    public Venue requestToDomain(VenueRequest request) {
        if (request == null) {
            return null;
        }
        
        Venue venue = new Venue();
        venue.setName(request.name());
        venue.setAddress(request.address());
        venue.setCapacity(request.capacity());
        
        return venue;
    }

    public VenueResponse domainToResponse(Venue venue) {
        if (venue == null) {
            return null;
        }
        
        return new VenueResponse(
            venue.getId(),
            venue.getName(),
            venue.getAddress(),
            venue.getCapacity()
        );
    }
}
