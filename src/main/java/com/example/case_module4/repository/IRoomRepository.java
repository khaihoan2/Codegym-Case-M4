package com.example.case_module4.repository;

import com.example.case_module4.model.Room;
import com.example.case_module4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room,Long> {
    Page<Room> findAll(Pageable pageable);

    @Query(value = "call find(?1,?2,?3,?4,?5,?6)",nativeQuery = true)
    Page<Room> find(String nameCity, String nameCategory, double areaRoom, int bedsRoom, double priceRoom, int bathsRoom, Pageable pageable);
}
