package com.codeup.eventapp.domain.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codeup.eventapp.application.dto.VenueRequest;
import com.codeup.eventapp.application.dto.VenueResponse;
import com.codeup.eventapp.domain.model.Venue;
import com.codeup.eventapp.domain.port.input.VenueUseCase;
import com.codeup.eventapp.domain.port.output.VenueRepositoryPort;
import com.codeup.eventapp.infrastructure.exception.ConflictException;
import com.codeup.eventapp.infrastructure.exception.NotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueService implements VenueUseCase {

    private final VenueRepositoryPort repositoryPort;
    private static final String NOT_FOUND = "Venue not found";

    @Transactional
    @Override
    public VenueResponse create(VenueRequest request) {
        if (repositoryPort.existsByName(request.name())) {
            throw new ConflictException("Duplicated venue name");
        }
        
        Venue venue = new Venue(null, request.name(), request.address(), request.capacity());
        Venue saved = repositoryPort.save(venue);
        
        return new VenueResponse(saved.getId(), saved.getName(), saved.getAddress(), saved.getCapacity());
    }

    @Transactional(readOnly = true)
    @Override
    public List<VenueResponse> findAll() {
        return repositoryPort.findAll().stream()
            .map(v -> new VenueResponse(v.getId(), v.getName(), v.getAddress(), v.getCapacity()))
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VenueResponse> findAll(Pageable pageable) {
        return repositoryPort.findAll(pageable)
            .map(v -> new VenueResponse(v.getId(), v.getName(), v.getAddress(), v.getCapacity()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VenueResponse> findAllDeleted(Pageable pageable) {
        return repositoryPort.findAllDeleted(pageable)
            .map(v -> new VenueResponse(v.getId(), v.getName(), v.getAddress(), v.getCapacity()));
    }

    @Transactional(readOnly = true)
    @Override
    public VenueResponse findById(Long id) {
        Venue venue = repositoryPort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        return new VenueResponse(venue.getId(), venue.getName(), venue.getAddress(), venue.getCapacity());
    }

    @Transactional
    @Override
    public VenueResponse update(Long id, VenueRequest request) {
        Venue existing = repositoryPort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

        if (!existing.getName().equalsIgnoreCase(request.name())
            && repositoryPort.existsByName(request.name())) {
            throw new ConflictException("Duplicated venue name");
        }

        existing.setName(request.name());
        existing.setAddress(request.address());
        existing.setCapacity(request.capacity());

        Venue updated = repositoryPort.save(existing);
        
        return new VenueResponse(updated.getId(), updated.getName(), updated.getAddress(), updated.getCapacity());
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        repositoryPort.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        repositoryPort.deleteById(id);
    }

    @Transactional
    @Override
    public void restore(Long id) {
        repositoryPort.findDeletedById(id)
            .orElseThrow(() -> new NotFoundException("Deleted venue not found"));
        repositoryPort.restore(id);
    }
}
