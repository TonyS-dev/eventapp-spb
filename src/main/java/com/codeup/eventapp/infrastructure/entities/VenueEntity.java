package com.codeup.eventapp.infrastructure.entities;

import com.codeup.eventapp.infrastructure.entities.EventEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import java.util.List;

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

    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<EventEntity> events;
}
