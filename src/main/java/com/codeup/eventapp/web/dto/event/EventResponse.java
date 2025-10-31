package com.codeup.eventapp.web.dto.event;

import java.time.LocalDateTime;

import com.codeup.eventapp.web.dto.venue.VenueResponse;

public record EventResponse(Long id, String name, String location, LocalDateTime date, String description, VenueResponse venue) {}
