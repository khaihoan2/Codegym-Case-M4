package com.example.case_module4.model.dto;

import com.example.case_module4.model.Room;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class BookingForm {

    private Room room;

    @NotEmpty
    private String checkIn;

    @NotEmpty
    private String checkOut;

    @NotNull
    private int adults;

    private int children;

    public BookingForm() {
    }
}
