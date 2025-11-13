package com.codeup.eventapp.domain.port.input;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.application.dto.VenueRequest;
import com.codeup.eventapp.application.dto.VenueResponse;

public interface VenueUseCase {
    VenueResponse create(VenueRequest request);
    List<VenueResponse> findAll();
    Page<VenueResponse> findAll(Pageable pageable);
    Page<VenueResponse> findAllDeleted(Pageable pageable);
    VenueResponse findById(Long id);
    VenueResponse update(Long id, VenueRequest request);
    void deleteById(Long id);
    void restore(Long id);
}
