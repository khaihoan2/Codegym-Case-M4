package com.example.case_module4.controller;

import com.example.case_module4.model.Booking;
import com.example.case_module4.service.booking.IBookingService;
import com.example.case_module4.service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin("*")
public class BookingRestController {
    @Autowired
    private IBookingService bookingService;

    @Autowired
    private IRoomService roomService;

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

    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking newBooking = bookingService.save(booking);
        booking.getRoom().setIsAvailable(
                !LocalDate.now().isAfter(booking.getCheckIn()) || !LocalDate.now().isBefore(booking.getCheckOut()));
        return new ResponseEntity<>(newBooking, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBooking(@PathVariable Long id,
                                                 @RequestBody Booking booking) {
        booking.setId(id);
        Booking newBooking = bookingService.save(booking);
        booking.getRoom().setIsAvailable(
                !LocalDate.now().isAfter(booking.getCheckIn()) || !LocalDate.now().isBefore(booking.getCheckOut()));
        return new ResponseEntity<>(newBooking, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> removeBooking(@PathVariable Long id) {
        bookingService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
