package com.codeup.eventapp.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.codeup.eventapp.entity.EventEntity;
import com.codeup.eventapp.exception.ConflictException;
import com.codeup.eventapp.exception.NotFoundException;
import com.codeup.eventapp.repository.IEventRepository;
import com.codeup.eventapp.service.IEventService;
import com.codeup.eventapp.web.dto.EventRequest;
import com.codeup.eventapp.web.dto.EventResponse;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements IEventService {

    private final IEventRepository repo;
    private static final String NOT_FOUND = "Event not found";

    @Transactional
    @Override
    public EventResponse create(EventRequest req) {
        if (repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Duplicated event name");
        }
        
        EventEntity saved = repo.save(EventEntity.builder()
                .name(req.name())
                .location(req.location())
                .date(req.date())
                .description(req.description())
                .build()
        );

        return new EventResponse(saved.getId(), saved.getName(), saved.getLocation(), saved.getDate(), saved.getDescription());
    }

    @Transactional(readOnly = true)
    @Override
    public EventResponse get(Long id) {
        return repo.findById(id)
            .map(u -> new EventResponse(u.getId(), u.getName(), u.getLocation(), u.getDate(), u.getDescription()))
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EventResponse> list(Pageable pageable) {
        return repo.findAll(pageable)
            .map(u -> new EventResponse(u.getId(), u.getName(), u.getLocation(), u.getDate(), u.getDescription()));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EventResponse> listDeleted(Pageable pageable) {
        return repo.findAllDeleted(pageable)
            .map(u -> new EventResponse(u.getId(), u.getName(), u.getLocation(), u.getDate(), u.getDescription()));
    }

    @Transactional
    @Override
    public EventResponse update(Long id, EventRequest req) {
        EventEntity existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));

    if (!existing.getName().equalsIgnoreCase(req.name())
        && repo.existsByNameIgnoreCase(req.name())) {
            throw new ConflictException("Duplicated event name");
        }

        existing.setName(req.name());
        existing.setLocation(req.location());
        existing.setDate(req.date());
        existing.setDescription(req.description());

        EventEntity updated = repo.save(existing);

        return new EventResponse(updated.getId(), updated.getName(), updated.getLocation(), updated.getDate(), updated.getDescription());
    }

    @Transactional
    @Override
    public void delete(Long id) {
        EventEntity existing = repo.findById(id)
            .orElseThrow(() -> new NotFoundException(NOT_FOUND));
        repo.delete(existing);
    }

    @Transactional
    @Override
    public void restore(Long id) {
        repo.findDeleted(id)
            .orElseThrow(() -> new NotFoundException("Deleted event not found"));
        repo.restore(id);
    }
}