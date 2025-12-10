package com.codeup.eventapp.events.infrastructure.entities;

import com.codeup.eventapp.venues.infrastructure.entities.VenueEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;

import java.time.LocalDateTime;

/**
 * JPA Entity for Event persistence.
 * This is an infrastructure concern and should not be used outside the infrastructure layer.
 */
@Entity
@Table(name = "events")
@SoftDelete
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class EventEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;
    private String location;
    private LocalDateTime date;
    
    @Column(length = 1000)
    private String description;

    @ManyToOne
    private VenueEntity venue;
}
