package com.codeup.eventapp.web.controller;

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

import com.codeup.eventapp.service.impl.EventServiceImpl;
import com.codeup.eventapp.web.dto.EventRequest;
import com.codeup.eventapp.web.dto.EventResponse;
import com.codeup.eventapp.web.response.ApiResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventServiceImpl service;

    @PostMapping
    public ResponseEntity<ApiResponse<EventResponse>> crear(
        @Valid @RequestBody EventRequest req,
            UriComponentsBuilder uri) {

        EventResponse res = service.create(req);
        URI location = uri.path("/api/events/{id}")
                .buildAndExpand(res.id()).toUri();

        var meta = new ApiResponse.Meta(
                "Event created successfully",
                com.codeup.eventapp.util.Trace.currentId(),
                "v1",
                null
        );

        return ResponseEntity.created(location)
                .body(ApiResponse.withMeta(res, meta));
    }
    
    @GetMapping
    public ResponseEntity<ApiResponse<List<EventResponse>>> list(Pageable pageable) {
        Page<EventResponse> page = service.list(pageable);
        return ResponseEntity.ok(ApiResponse.withPage(page.getContent(), page));
    }

    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<List<EventResponse>>> listDeleted(Pageable pageable) {
        Page<EventResponse> page = service.listDeleted(pageable);
        return ResponseEntity.ok(ApiResponse.withPage(page.getContent(), page));
    }    

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> get(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(service.get(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EventResponse>> update(@PathVariable Long id, @Valid @RequestBody EventRequest req) {
        EventResponse res = service.update(id, req);
        return ResponseEntity.ok(ApiResponse.success(res));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restore(@PathVariable Long id) {
        service.restore(id);
        var meta = new ApiResponse.Meta(
                "Event restored",
                com.codeup.eventapp.util.Trace.currentId(),
                "v1",
                null
        );
        return ResponseEntity.ok(ApiResponse.withMeta(null, meta));
    }
}