package com.codeup.eventapp.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record EventRequest(
        @NotBlank @Size(max = 120) String name,
        @NotBlank @Size(max = 250) String location,
        @NotBlank String date,
        @Size(max = 1000) String description
        
) {}

