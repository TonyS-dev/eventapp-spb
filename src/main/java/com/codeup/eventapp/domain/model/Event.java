package com.codeup.eventapp.domain.model;

import java.time.LocalDateTime;

/**
 * Domain model representing an Event.
 * This is a pure domain object without infrastructure dependencies.
 */
public class Event {
    private Long id;
    private String name;
    private String location;
    private LocalDateTime date;
    private String description;
    private Venue venue;

    public Event() {
    }

    public Event(Long id, String name, String location, LocalDateTime date, String description, Venue venue) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
        this.venue = venue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", date=" + date +
                ", description='" + description + '\'' +
                ", venue=" + (venue != null ? venue.getName() : "null") +
                '}';
    }
}
