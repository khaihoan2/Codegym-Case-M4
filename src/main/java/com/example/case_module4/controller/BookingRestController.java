package com.example.case_module4.controller;

import com.example.case_module4.model.Booking;
import com.example.case_module4.service.booking.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
public class BookingRestController {
    @Autowired
    private IBookingService bookingService;

    @GetMapping
    public ResponseEntity<Page<Booking>> findAll(Pageable pageable) {
        Page<Booking> bookings = bookingService.findAll(pageable);
        if (bookings.hasContent()) {
            return new ResponseEntity<>(bookings, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> findById(@PathVariable Long id) {
        Optional<Booking> bookingOptional = bookingService.findById(id);
        return new ResponseEntity<>(bookingOptional.get(), HttpStatus.OK);
    }
}
