package com.example.case_module4.repository;

import com.example.case_module4.model.Category;
import com.example.case_module4.model.City;
import com.example.case_module4.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room,Long> {
    Page<Room> findAll(Pageable pageable);

    Iterable<Room> findAllByCategory(Category category);

    Iterable<Room> findAllByCity(City city);

    @Query(value = "call find_room(?1,?2,?3,?4,?5,?6,?7,?8)",nativeQuery = true)
    Page<Room> find_room(String nameCity, String nameCategory, Double minAreaRoom,Double maxAreaRoom, String bedsRoom, Double minPriceRoom, Double maxPriceRoom, String bathsRoom, Pageable pageable);
}
