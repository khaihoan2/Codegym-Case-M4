package com.example.case_module4.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "host_user_id")
    private User host;

    @NotEmpty
    private double area;

    @NotEmpty
    private double price;

    @NotNull
    private int beds;

    @NotNull
    private int baths;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @NotEmpty
    private String address;

    private double avgRating;

    private boolean isAvailable;

    public Room() {
    }
}
