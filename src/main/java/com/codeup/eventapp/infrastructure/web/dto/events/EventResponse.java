package com.codeup.eventapp.infrastructure.web.dto.events;

import java.time.LocalDateTime;

import com.codeup.eventapp.infrastructure.web.dto.venues.VenueResponse;

public record EventResponse(Long id, String name, String location, LocalDateTime date, String description, VenueResponse venue) {}
