package com.codeup.eventapp.domain;


public class Event {
    private Long id;
    private String name;
    private String location;
    private String date;
    private String description;

    public Event(Long id, String name, String location, String date, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.date = date;
        this.description = description;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return "Event [id=" + id + ", name=" + name + ", location=" + location + ", date=" + date + ", description="
                + description + "]";
    }
}
