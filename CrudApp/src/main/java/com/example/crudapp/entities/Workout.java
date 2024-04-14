package com.example.crudapp.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer WorkoutID;
    @Column(nullable = false)
    private String Name;
    @Column(nullable = false)
    private LocalDateTime Date;
    @Column(nullable = false)
    private Integer Places;
    @Column(nullable = false)
    private String Location;


    public Workout() {}

    public Workout(String name, LocalDateTime date, Integer places, String location) {
        this.Name = name;
        this.Date = date;
        this.Places = places;
        this.Location = location;
    }

    public Integer getWorkoutID() {
        return WorkoutID;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public void setDate(LocalDateTime date) {
        Date = date;
    }

    public Integer getPlaces() {
        return Places;
    }

    public void setPlaces(Integer places) {
        Places = places;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    @Override
    public String toString() {
        return "Workout{" +
                "WorkoutID=" + WorkoutID +
                ", Name='" + Name + '\'' +
                ", Date='" + Date + '\'' +
                ", Places=" + Places +
                ", Location='" + Location + '\'' +
                '}';
    }
}
