package com.codeup.eventapp.events.infrastructure.adapters;

import com.codeup.eventapp.events.domain.ports.out.EventRepositoryPort;
import com.codeup.eventapp.events.domain.model.Event;
import com.codeup.eventapp.events.infrastructure.entities.EventEntity;
import com.codeup.eventapp.events.infrastructure.mappers.EventMapper;
import com.codeup.eventapp.events.infrastructure.repositories.JpaEventRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Adapter that implements the EventRepositoryPort using JPA.
 * This connects the domain layer to the infrastructure layer.
 */
@Component
public class EventRepositoryAdapter implements EventRepositoryPort {

    private final JpaEventRepository jpaRepository;
    private final EventMapper mapper;

    public EventRepositoryAdapter(JpaEventRepository jpaRepository, EventMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Event save(Event event) {
        EventEntity entity = mapper.toEntity(event);
        EventEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Event> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Page<Event> findAllDeleted(Pageable pageable) {
        return jpaRepository.findAllDeleted(pageable)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<Event> findDeleted(Long id) {
        return jpaRepository.findDeleted(id)
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByNameIgnoreCase(String name) {
        return jpaRepository.existsByNameIgnoreCase(name);
    }

    @Override
    public void delete(Event event) {
        EventEntity entity = mapper.toEntity(event);
        jpaRepository.delete(entity);
    }

    @Override
    public void restore(Long id) {
        jpaRepository.restore(id);
    }
}
