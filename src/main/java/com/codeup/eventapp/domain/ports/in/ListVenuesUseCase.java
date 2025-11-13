package com.codeup.eventapp.domain.ports.in;

import com.codeup.eventapp.domain.model.Venue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Use case for retrieving all venues with pagination.
 */
public interface ListVenuesUseCase {
    Page<Venue> execute(Pageable pageable);
}
