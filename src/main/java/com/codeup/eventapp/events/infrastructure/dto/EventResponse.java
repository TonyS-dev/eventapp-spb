package com.codeup.eventapp.events.infrastructure.dto;

import com.codeup.eventapp.venues.infrastructure.dto.VenueResponse;

import java.time.LocalDateTime;

public record EventResponse(Long id, String name, String location, LocalDateTime date, String description, VenueResponse venue) {}
