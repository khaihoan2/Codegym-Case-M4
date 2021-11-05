package com.example.case_module4.repository;

import com.example.case_module4.model.IRoomRating;
import com.example.case_module4.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface IRoomRepository extends JpaRepository<Room,Long> {

    @Query(value = "select * from find_room_by_avg_rating", nativeQuery = true)
    Page<IRoomRating> findRoomRating(Pageable pageable);
}
