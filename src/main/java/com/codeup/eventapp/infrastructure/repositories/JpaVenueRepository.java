package com.codeup.eventapp.infrastructure.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codeup.eventapp.infrastructure.entities.VenueEntity;
import java.util.Optional;

/**
 * JPA Repository for VenueEntity.
 * This is an infrastructure component.
 */
@Repository
public interface JpaVenueRepository extends JpaRepository<VenueEntity, Long> {
    boolean existsByNameIgnoreCase(String name);

    // Query deleted venues (bypasses @SoftDelete filter)
    @Query(value = "SELECT * FROM venues WHERE deleted = true", nativeQuery = true)
    Page<VenueEntity> findAllDeleted(Pageable pageable);
    
    @Query(value = "SELECT * FROM venues WHERE id = ?1 AND deleted = true", nativeQuery = true)
    Optional<VenueEntity> findDeleted(Long id);

    // Restore soft-deleted venue (native SQL)
    @Modifying
    @Query(value = "UPDATE venues SET deleted = false WHERE id = ?1", nativeQuery = true)
    void restore(Long id);
}
