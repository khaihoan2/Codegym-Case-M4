package com.example.case_module4.service.room;

import com.example.case_module4.model.IRoomRating;
import com.example.case_module4.model.Category;
import com.example.case_module4.model.City;
import com.example.case_module4.model.Room;
import com.example.case_module4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomService extends IGeneralService<Room> {

    Page<IRoomRating> findRoomRating(Pageable pageable);

   Page<Room> findAll(Pageable pageable);

   Iterable<Room> findAllByCategory(Category category);

   Iterable<Room> findAllByCity(City city);

   Iterable<Room> findRoom(String nameCity, String nameCategory, Double minAreaRoom, Double maxAreaRoom, String bedsRoom, Double minPriceRoom, Double maxPriceRoom, String bathsRoom, int size, int position);
}
