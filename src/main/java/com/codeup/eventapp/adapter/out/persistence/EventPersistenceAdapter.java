package com.codeup.eventapp.adapter.out.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.codeup.eventapp.application.port.out.EventPersistencePort;
import com.codeup.eventapp.domain.model.Event;
import com.codeup.eventapp.entity.EventEntity;
import com.codeup.eventapp.entity.VenueEntity;
import com.codeup.eventapp.repository.IEventRepository;

import lombok.RequiredArgsConstructor;

import java.util.Optional;

/**
 * Adapter that implements EventPersistencePort using JPA
 * Translates between domain models and JPA entities
 */
@Component
@RequiredArgsConstructor
public class EventPersistenceAdapter implements EventPersistencePort {

    private final IEventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public Event save(Event event) {
        EventEntity entity = eventMapper.toEntity(event);
        EventEntity saved = eventRepository.save(entity);
        return eventMapper.toDomain(saved);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id)
            .map(eventMapper::toDomain);
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        return eventRepository.findAll(pageable)
            .map(eventMapper::toDomain);
    }

    @Override
    public Page<Event> findAllDeleted(Pageable pageable) {
        return eventRepository.findAllDeleted(pageable)
            .map(eventMapper::toDomain);
    }

    @Override
    public Optional<Event> findDeleted(Long id) {
        return eventRepository.findDeleted(id)
            .map(eventMapper::toDomain);
    }

    @Override
    public void delete(Event event) {
        EventEntity entity = eventMapper.toEntity(event);
        eventRepository.delete(entity);
    }

    @Override
    public void restore(Long id) {
        eventRepository.restore(id);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return eventRepository.existsByNameIgnoreCase(name);
    }
}
