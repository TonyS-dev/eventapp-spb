package com.codeup.eventapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.web.dto.venue.VenueRequest;
import com.codeup.eventapp.web.dto.venue.VenueResponse;

public interface IVenueService {
    VenueResponse create(VenueRequest req);
    VenueResponse get(Long id);
    Page<VenueResponse> list(Pageable pageable);
    Page<VenueResponse> listDeleted(Pageable pageable);
    VenueResponse update(Long id, VenueRequest req);
    void delete(Long id);
    void restore(Long id);
}
