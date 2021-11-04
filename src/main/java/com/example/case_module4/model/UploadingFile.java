package com.example.case_module4.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@Entity
public class UploadingFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(unique = true)
    private String name;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;


    public UploadingFile(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public UploadingFile(String name, Room room) {
        this.name = name;
        this.room = room;
    }

    public UploadingFile() {

    }
}
