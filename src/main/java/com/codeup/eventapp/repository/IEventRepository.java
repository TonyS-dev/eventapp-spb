package com.codeup.eventapp.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.codeup.eventapp.entity.EventEntity;
import java.util.Optional;

@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
    boolean existsByNameIgnoreCase(String name);

    // Query deleted events (bypasses @SoftDelete filter)
    @Query(value = "SELECT * FROM events WHERE deleted = true", nativeQuery = true)
    Page<EventEntity> findAllDeleted(Pageable pageable);
    
    @Query(value = "SELECT * FROM events WHERE id = ?1 AND deleted = true", nativeQuery = true)
    Optional<EventEntity> findDeleted(Long id);

    // Restore soft-deleted event (native SQL)
    @Modifying
    @Query(value = "UPDATE events SET deleted = false WHERE id = ?1", nativeQuery = true)
    void restore(Long id);
}
