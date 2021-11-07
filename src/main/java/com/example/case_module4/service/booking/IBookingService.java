package com.example.case_module4.service.booking;

import com.example.case_module4.exception.BadRequestException;
import com.example.case_module4.model.Booking;
import com.example.case_module4.model.User;
import com.example.case_module4.model.dto.BookingForm;
import com.example.case_module4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IBookingService extends IGeneralService<Booking> {
     boolean checkRoomAvailable(Long id);
    Page<Booking> findAllByGuest(User guest, Pageable pageable);

    Page<Booking> findAll(Pageable pageable);

    Booking save(BookingForm bookingForm) throws BadRequestException;
}
