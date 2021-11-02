package com.example.case_module4.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "guest_user_id")
    private User guest;

    private LocalDate checkIn;

    private LocalDate checkOut;

    private int adults;

    private int children;

    public Booking() {
    }
}
