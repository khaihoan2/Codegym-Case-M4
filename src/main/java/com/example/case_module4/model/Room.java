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

    private Double area;

    private Double price;

    private Integer beds;

    private Integer baths;

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    private String address;

    private Boolean isAvailable;

    public Room() {
    }
}
