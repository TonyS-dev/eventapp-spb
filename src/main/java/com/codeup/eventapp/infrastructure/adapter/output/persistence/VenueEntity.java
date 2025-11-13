package com.codeup.eventapp.infrastructure.adapter.output.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.hibernate.annotations.SoftDelete;

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
