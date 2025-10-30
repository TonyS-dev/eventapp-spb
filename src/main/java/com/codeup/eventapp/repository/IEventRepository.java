package com.codeup.eventapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeup.eventapp.entity.EventEntity;

@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
    boolean existsByNameIgnoreCase(String name);
}
