package com.codeup.eventapp.adapter.in.web.dto.venue;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record VenueRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 250) String address,
        @NotNull @Min(1) int capacity
) {}

