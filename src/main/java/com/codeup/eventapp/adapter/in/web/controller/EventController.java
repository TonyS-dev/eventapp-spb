package com.codeup.eventapp.adapter.in.web.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.codeup.eventapp.adapter.in.web.dto.event.EventRequest;
import com.codeup.eventapp.adapter.in.web.dto.event.EventResponse;
import com.codeup.eventapp.adapter.in.web.mapper.EventDtoMapper;
import com.codeup.eventapp.adapter.in.web.response.AppResponse;
import com.codeup.eventapp.application.port.in.EventUseCase;
import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.util.Trace;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * REST Controller for Event management (Input Adapter)
 * Adapts HTTP requests to domain use cases
 */
@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
@Tag(name = "Events", description = "Event management APIs")
public class EventController {

    private final EventUseCase eventUseCase;
    private final EventDtoMapper mapper;

    @Operation(
        summary = "Create a new event", 
        description = "Creates a new event with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Event created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "data": {
                        "id": 1,
                        "name": "New Event",
                        "location": "Downtown Hall",
                        "date": "2025-12-15T20:00:00",
                        "description": "A fun event",
                        "venue": {
                          "id": 1,
                          "name": "Grand Hall",
                          "address": "123 Main St",
                          "capacity": 500
                        }
                      },
                      "meta": {
                        "message": "Event created successfully",
                        "traceId": "...",
                        "apiVersion": "v1"
                      }
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "409", description = "Event name already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AppResponse<EventResponse>> crear(
        @Valid @RequestBody EventRequest req,
            UriComponentsBuilder uri) {

        Event event = mapper.toDomain(req);
        Event created = eventUseCase.createEvent(event);
        EventResponse res = mapper.toResponse(created);
        
        URI location = uri.path("/api/events/{id}")
                .buildAndExpand(res.id()).toUri();

        var meta = new AppResponse.Meta(
                "Event created successfully",
                Trace.currentId(),
                "v1",
                null
        );

        return ResponseEntity.created(location)
                .body(AppResponse.withMeta(res, meta));
    }
    
    @Operation(
        summary = "List all events", 
        description = "Retrieves a paginated list of all active events"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "List of events retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "data": [
                        {
                          "id": 1,
                          "name": "Concert Night",
                          "location": "Central Park",
                          "date": "2025-12-15T20:00:00",
                          "description": "An amazing concert",
                          "venue": {
                            "id": 1,
                            "name": "Grand Hall",
                            "address": "123 Main St",
                            "capacity": 500
                          }
                        }
                      ],
                      "meta": {
                        "message": "OK",
                        "traceId": "...",
                        "apiVersion": "v1",
                        "page": {
                          "number": 0,
                          "size": 20,
                          "totalElements": 1,
                          "totalPages": 1
                        }
                      }
                    }
                    """
                )
            )
        )
    })
    @GetMapping
    public ResponseEntity<AppResponse<List<EventResponse>>> list(
        @Parameter(description = "Pagination parameters") Pageable pageable) {
        Page<Event> page = eventUseCase.listEvents(pageable);
        List<EventResponse> responses = page.map(mapper::toResponse).getContent();
        return ResponseEntity.ok(AppResponse.withPage(responses, page));
    }

    @Operation(
        summary = "List deleted events", 
        description = "Retrieves a paginated list of soft-deleted events"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "List of deleted events retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class)
            )
        )
    })
    @GetMapping("/deleted")
    public ResponseEntity<AppResponse<List<EventResponse>>> listDeleted(
        @Parameter(description = "Pagination parameters") Pageable pageable) {
        Page<Event> page = eventUseCase.listDeletedEvents(pageable);
        List<EventResponse> responses = page.map(mapper::toResponse).getContent();
        return ResponseEntity.ok(AppResponse.withPage(responses, page));
    }    

    @Operation(
        summary = "Get event by ID", 
        description = "Retrieves a specific event by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Event found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "data": {
                        "id": 1,
                        "name": "Concert Night",
                        "location": "Central Park",
                        "date": "2025-12-15T20:00:00",
                        "description": "An amazing concert",
                        "venue": {
                          "id": 1,
                          "name": "Grand Hall",
                          "address": "123 Main St",
                          "capacity": 500
                        }
                      },
                      "meta": {
                        "message": "OK",
                        "traceId": "...",
                        "apiVersion": "v1"
                      }
                    }
                    """
                )
            )
        ),
        @ApiResponse(
            responseCode = "404", 
            description = "Event not found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                        "timestamp": "2023-10-27T10:30:00",
                        "status": 404,
                        "error": "Not Found",
                        "message": "Event with id 999 not found",
                        "path": "/api/events/999"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<EventResponse>> get(
        @Parameter(description = "Event ID") @PathVariable Long id) {
        Event event = eventUseCase.getEvent(id);
        return ResponseEntity.ok(AppResponse.success(mapper.toResponse(event)));
    }

    @Operation(
        summary = "Update event", 
        description = "Updates an existing event with new details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Event updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Event name already exists", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<EventResponse>> update(
        @Parameter(description = "Event ID") @PathVariable Long id, 
        @Valid @RequestBody EventRequest req) {
        Event event = mapper.toDomain(req);
        Event updated = eventUseCase.updateEvent(id, event);
        return ResponseEntity.ok(AppResponse.success(mapper.toResponse(updated)));
    }

    @Operation(
        summary = "Delete event", 
        description = "Soft deletes an event by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Event deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Event not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "Event ID") @PathVariable Long id) {
        eventUseCase.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Restore deleted event", 
        description = "Restores a soft-deleted event"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Event restored successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Deleted event not found", content = @Content)
    })
    @PutMapping("/{id}/restore")
    public ResponseEntity<AppResponse<Void>> restore(
        @Parameter(description = "Event ID") @PathVariable Long id) {
        eventUseCase.restoreEvent(id);
        var meta = new AppResponse.Meta(
                "Event restored",
                Trace.currentId(),
                "v1",
                null
        );
        return ResponseEntity.ok(AppResponse.withMeta(null, meta));
    }
}
