package com.codeup.eventapp.infrastructure.entities;

import com.codeup.eventapp.infrastructure.entities.VenueEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import java.time.LocalDateTime;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "venue_id")
    private VenueEntity venue;
}
