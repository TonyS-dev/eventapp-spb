package com.codeup.eventapp.infrastructure.adapter.input.rest;

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

import com.codeup.eventapp.domain.port.input.VenueUseCase;
import com.codeup.eventapp.application.dto.VenueRequest;
import com.codeup.eventapp.application.dto.VenueResponse;
import com.codeup.eventapp.infrastructure.adapter.input.rest.response.AppResponse;
import com.codeup.eventapp.infrastructure.util.Trace;

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


@RestController
@RequestMapping("/api/venues")
@RequiredArgsConstructor
@Tag(name = "Venues", description = "Venue management APIs")
public class VenueRestController {

    private final VenueUseCase venueUseCase;

    @Operation(
        summary = "Create a new venue", 
        description = "Creates a new venue with the provided details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "201", 
            description = "Venue created successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "data": {
                        "id": 1,
                        "name": "New Venue",
                        "address": "456 Oak Ave",
                        "capacity": 200
                      },
                      "meta": {
                        "message": "Venue created successfully",
                        "traceId": "...",
                        "apiVersion": "v1"
                      }
                    }
                    """
                )
            )
        ),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
        @ApiResponse(responseCode = "409", description = "Venue name already exists", content = @Content)
    })
    @PostMapping
    public ResponseEntity<AppResponse<VenueResponse>> crear(
        @Valid @RequestBody VenueRequest req,
            UriComponentsBuilder uri) {

        VenueResponse res = venueUseCase.create(req);
        URI location = uri.path("/api/venues/{id}")
                .buildAndExpand(res.id()).toUri();

        var meta = new AppResponse.Meta(
                "Venue created successfully",
                Trace.currentId(),
                "v1",
                null
        );

        return ResponseEntity.created(location)
                .body(AppResponse.withMeta(res, meta));
    }
    
    @Operation(
        summary = "List all venues", 
        description = "Retrieves a paginated list of all active venues"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "List of venues retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "data": [
                        {
                          "id": 1,
                          "name": "Grand Hall",
                          "address": "123 Main St",
                          "city": "Metropolis",
                          "state": "NY",
                          "capacity": 500
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
    public ResponseEntity<AppResponse<List<VenueResponse>>> list(
        @Parameter(description = "Pagination parameters") Pageable pageable) {
        Page<VenueResponse> page = venueUseCase.findAll(pageable);
        return ResponseEntity.ok(AppResponse.withPage(page.getContent(), page));
    }

    @Operation(
        summary = "List deleted venues", 
        description = "Retrieves a paginated list of soft-deleted venues"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "List of deleted venues retrieved successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class)
            )
        )
    })
    @GetMapping("/deleted")
    public ResponseEntity<AppResponse<List<VenueResponse>>> listDeleted(
        @Parameter(description = "Pagination parameters") Pageable pageable) {
        Page<VenueResponse> page = venueUseCase.findAllDeleted(pageable);
        return ResponseEntity.ok(AppResponse.withPage(page.getContent(), page));
    }    

    @Operation(
        summary = "Get venue by ID", 
        description = "Retrieves a specific venue by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Venue found",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class),
                examples = @ExampleObject(
                    value = """
                    {
                      "data": {
                        "id": 1,
                        "name": "Grand Hall",
                        "address": "123 Main St",
                        "city": "Metropolis",
                        "state": "NY",
                        "capacity": 500
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
            description = "Venue not found",
            content = @Content(
                mediaType = "application/json",
                examples = @ExampleObject(
                    value = """
                    {
                        "timestamp": "2023-10-27T10:30:00",
                        "status": 404,
                        "error": "Not Found",
                        "message": "Venue with id 999 not found",
                        "path": "/api/venues/999"
                    }
                    """
                )
            )
        )
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppResponse<VenueResponse>> get(
        @Parameter(description = "Venue ID") @PathVariable Long id) {
        return ResponseEntity.ok(AppResponse.success(venueUseCase.findById(id)));
    }

    @Operation(
        summary = "Update venue", 
        description = "Updates an existing venue with new details"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Venue updated successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content),
        @ApiResponse(responseCode = "409", description = "Venue name already exists", content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<AppResponse<VenueResponse>> update(
        @Parameter(description = "Venue ID") @PathVariable Long id, 
        @Valid @RequestBody VenueRequest req) {
        VenueResponse res = venueUseCase.update(id, req);
        return ResponseEntity.ok(AppResponse.success(res));
    }

    @Operation(
        summary = "Delete venue", 
        description = "Soft deletes a venue by its ID"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Venue deleted successfully", content = @Content),
        @ApiResponse(responseCode = "404", description = "Venue not found", content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
        @Parameter(description = "Venue ID") @PathVariable Long id) {
        venueUseCase.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
        summary = "Restore deleted venue", 
        description = "Restores a soft-deleted venue"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200", 
            description = "Venue restored successfully",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = AppResponse.class)
            )
        ),
        @ApiResponse(responseCode = "404", description = "Deleted venue not found", content = @Content)
    })
    @PutMapping("/{id}/restore")
    public ResponseEntity<AppResponse<Void>> restore(@PathVariable Long id) {
        venueUseCase.restore(id);
        var meta = new AppResponse.Meta(
                "Venue restored",
                Trace.currentId(),
                "v1",
                null
        );
        return ResponseEntity.ok(AppResponse.withMeta(null, meta));
    }
}