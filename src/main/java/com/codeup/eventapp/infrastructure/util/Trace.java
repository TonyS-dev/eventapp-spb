package com.codeup.eventapp.infrastructure.util;

import java.util.Optional;
import java.util.UUID;

/**
 * Utility for tracing requests across the infrastructure layer.
 * Uses SLF4J MDC for distributed tracing.
 */
public final class Trace {
    private Trace() {}
    
    public static String currentId() {
        return Optional.ofNullable(org.slf4j.MDC.get("traceId"))
                       .orElse(UUID.randomUUID().toString());
    }
}
