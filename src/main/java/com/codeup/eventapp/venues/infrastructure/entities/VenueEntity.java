package com.codeup.eventapp.venues.infrastructure.entities;

import com.codeup.eventapp.events.infrastructure.entities.EventEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.util.List;

/**
 * JPA Entity for Venue persistence.
 * This is an infrastructure concern and should not be used outside the infrastructure layer.
 */
@Entity
@Table(name = "venues")
@SoftDelete
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class VenueEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "venue")
    private List<EventEntity> events;
}
