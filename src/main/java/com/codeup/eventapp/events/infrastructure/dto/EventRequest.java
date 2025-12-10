package com.codeup.eventapp.events.infrastructure.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public record EventRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 250) String location,
        @NotNull LocalDateTime date,
        @Size(max = 1000) String description,
        Long venueId
) {}

