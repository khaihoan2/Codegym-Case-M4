package com.example.case_module4.service.booking;

import com.example.case_module4.model.Booking;
import com.example.case_module4.model.User;
import com.example.case_module4.repository.IBookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Override
    public Iterable<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        return bookingRepository.save(booking);
    }

    @Override
    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    @Override
    public Page<Booking> findAllByGuest(User guest, Pageable pageable) {
        return bookingRepository.findAllByGuest(guest, pageable);
    }

    @Override
    public Page<Booking> findAll(Pageable pageable) {
        return bookingRepository.findAll(pageable);
    }
    @Override
    public boolean checkRoomAvailable(Long id) {
        bookingRepository.checkRoomAvailable(id);
        return true;
    }
}
