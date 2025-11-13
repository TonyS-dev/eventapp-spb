package com.codeup.eventapp.adapter.in.web.mapper;

import org.springframework.stereotype.Component;

import com.codeup.eventapp.adapter.in.web.dto.venue.VenueRequest;
import com.codeup.eventapp.adapter.in.web.dto.venue.VenueResponse;
import com.codeup.eventapp.domain.model.Venue;

/**
 * Mapper to convert between Venue domain model and web DTOs
 */
@Component
public class VenueDtoMapper {

    public Venue toDomain(VenueRequest request) {
        if (request == null) {
            return null;
        }
        return new Venue(
            null,
            request.name(),
            request.address(),
            request.capacity()
        );
    }

    public VenueResponse toResponse(Venue venue) {
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
