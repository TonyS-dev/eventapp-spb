package com.codeup.eventapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeup.eventapp.entity.VenueEntity;
import com.codeup.eventapp.exception.ConflictException;
import com.codeup.eventapp.exception.NotFoundException;
import com.codeup.eventapp.repository.IVenueRepository;
import com.codeup.eventapp.service.IVenueService;
import com.codeup.eventapp.web.dto.venue.VenueRequest;
import com.codeup.eventapp.web.dto.venue.VenueResponse;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VenueServiceImpl implements IVenueService {

    private final IVenueRepository repo;
    private static final String NOT_FOUND = "Venue not found";

    @Transactional
    @Override
    public VenueResponse create(VenueRequest req) {
        if (repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Duplicated venue name");
        }
        
        VenueEntity saved = repo.save(VenueEntity.builder()
                .name(req.name())
                .address(req.address())
                .capacity(req.capacity())
                .build()
        );

        return new VenueResponse(saved.getId(), saved.getName(), saved.getAddress(), saved.getCapacity());
    }

    @Transactional(readOnly = true)
    @Override
    public VenueResponse get(Long id) {
        return repo.findById(id)
            .map(u -> new VenueResponse(u.getId(), u.getName(), u.getAddress(), u.getCapacity()))
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VenueResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
            .map(u -> new VenueResponse(u.getId(), u.getName(), u.getAddress(), u.getCapacity()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<VenueResponse> listDeleted(Pageable pageable) {
        return repo.findAllDeleted(pageable)
            .map(u -> new VenueResponse(u.getId(), u.getName(), u.getAddress(), u.getCapacity()));
    }

    @Transactional
    @Override
    public VenueResponse update(Long id, VenueRequest req) {
        VenueEntity existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

    if (!existing.getName().equalsIgnoreCase(req.name())
        && repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Duplicated venue name");
        }

        existing.setName(req.name());
        existing.setAddress(req.address());
        existing.setCapacity(req.capacity());

        VenueEntity updated = repo.save(existing);

        return new VenueResponse(updated.getId(), updated.getName(), updated.getAddress(), updated.getCapacity());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        VenueEntity existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        repo.delete(existing);
    }

    @Transactional
    @Override
    public void restore(Long id) {
        repo.findDeleted(id)
            .orElseThrow(() -> new NotFoundException("Deleted venue not found"));
        repo.restore(id);
    }
}