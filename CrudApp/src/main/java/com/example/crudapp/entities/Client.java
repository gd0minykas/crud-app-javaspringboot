package com.example.crudapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Table
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ClientID;
    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String Name;
    @Column(nullable = false)
    @Size(min = 2, max = 50, message = "Surname must be between 2 and 50 characters")
    private String Surname;
    @Column(nullable = false, unique = true)
    @Size(max = 128, message = "Email must be under 128 characters")
    @Email(message = "Email should be valid")
    private String Email;
    @PositiveOrZero
    @Size(min = 7, max = 12, message = "Phone must be between 7 and 12 characters")
    private String Phone;
    private String Agreement = null;

    public Client() {}

    public Client(String name, String surname, String email, String phone) {
        Name = name;
        Surname = surname;
        Email = email;
        Phone = phone;
    }


    public Integer getClientID() {
        return ClientID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public String getAgreement() {
        return Agreement;
    }

    public void setAgreement(String agreement) {
        Agreement = agreement;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
    @Override
    public String toString() {
        return "Client{" +
                "ClientID=" + ClientID +
                ", Name='" + Name + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                '}';
    }
}