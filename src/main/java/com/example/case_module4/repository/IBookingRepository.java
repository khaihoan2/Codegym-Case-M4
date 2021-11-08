package com.example.case_module4.repository;

import com.example.case_module4.model.Booking;
import com.example.case_module4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookingRepository extends JpaRepository<Booking, Long> {
    Page<Booking> findAllByGuest(User guest, Pageable pageable);

    @Override
    Page<Booking> findAll(Pageable pageable);
}
