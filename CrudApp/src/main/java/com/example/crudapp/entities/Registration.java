package com.example.crudapp.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer RegID;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ClientID", referencedColumnName = "ClientID")
    private Client Client;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "WorkoutID", referencedColumnName = "WorkoutID")
    private Workout Workout;
    @Column(nullable = false)
    private LocalDateTime Date;

    public Registration() {}

    public Registration(Client ClientID, Workout WorkoutID, LocalDateTime Date) {
        this.Client = ClientID;
        this.Workout = WorkoutID;
        this.Date = Date;
    }

    public Integer getRegID() {
        return RegID;
    }

    public void setRegID(Integer regID) {
        RegID = regID;
    }

    public Client getClient() {
        return Client;
    }

    public void setClient(Client client) {
        Client = client;
    }

    public Workout getWorkout() {
        return Workout;
    }

    public void setWorkout(Workout workout) {
        Workout = workout;
    }

    public LocalDateTime getDate() {
        return Date;
    }

    public void setDate(LocalDateTime Date) {
        Date = Date;
    }

    @Override
    public String toString() {
        return "Registration{" +
                "RegID=" + RegID +
                ", Client=" + Client +
                ", Workout=" + Workout +
                ", RegDate='" + Date + '\'' +
                '}';
    }
}
