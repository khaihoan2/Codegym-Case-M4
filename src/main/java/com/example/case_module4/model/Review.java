package com.example.case_module4.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    private String content;

    private double rating;

    private LocalDateTime postedAt;

    public Review() {
    }
}
