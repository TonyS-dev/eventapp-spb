package com.codeup.eventapp.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.codeup.eventapp.web.dto.EventRequest;
import com.codeup.eventapp.web.dto.EventResponse;

public interface IEventService {
    EventResponse create(EventRequest req);
    EventResponse get(Long id);
    Page<EventResponse> list(Pageable pageable);
    Page<EventResponse> listDeleted(Pageable pageable);
    EventResponse update(Long id, EventRequest req);
    void delete(Long id);
    void restore(Long id);
}
