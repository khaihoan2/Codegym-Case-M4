package com.example.case_module4.service.booking;

import com.example.case_module4.exception.BadRequestException;
import com.example.case_module4.model.Booking;
import com.example.case_module4.model.Room;
import com.example.case_module4.model.User;
import com.example.case_module4.model.dto.BookingForm;
import com.example.case_module4.repository.IBookingRepository;
import com.example.case_module4.service.room.IRoomService;
import com.example.case_module4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BookingService implements IBookingService {

    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoomService roomService;

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
    public Booking save(BookingForm bookingForm) throws BadRequestException {
        Optional<Room> roomSearch = roomService.findById(bookingForm.getRoom().getId());
        if (!roomSearch.isPresent()) {
            throw new BadRequestException();
        }
        Room room = roomSearch.get();
        room.setIsAvailable(false);
        roomService.save(room);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<User> userSearch = userService.findByUsername(userDetails.getUsername());
        User user = userSearch.get();
        String checkInInput = bookingForm.getCheckIn();
        LocalDate checkIn = LocalDate.parse(checkInInput);
        String checkOutInput = bookingForm.getCheckOut();
        LocalDate checkOut = LocalDate.parse(checkOutInput);
        Booking booking = new Booking();
        booking.setRoom(room);
        booking.setAdults(bookingForm.getAdults());
        booking.setChildren(bookingForm.getChildren());
        booking.setGuest(user);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        return bookingRepository.save(booking);
    }
}
