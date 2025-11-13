package com.codeup.eventapp.application.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.domain.model.Venue;

/**
 * Input port for Venue use cases
 * Defines the business operations that can be performed on venues
 */
public interface VenueUseCase {
    Venue createVenue(Venue venue);
    Venue getVenue(Long id);
    Page<Venue> listVenues(Pageable pageable);
    Page<Venue> listDeletedVenues(Pageable pageable);
    Venue updateVenue(Long id, Venue venue);
    void deleteVenue(Long id);
    void restoreVenue(Long id);
}
