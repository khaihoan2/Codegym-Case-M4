package com.example.case_module4.repository;

import com.example.case_module4.model.Subscriber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISubscriberRepository extends JpaRepository<Subscriber,Long> {
    Page<Subscriber> findAll(Pageable pageable);
}
