package com.example.case_module4.service.room;

import com.example.case_module4.model.Room;
import com.example.case_module4.model.User;
import com.example.case_module4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomService extends IGeneralService<Room> {
   Page<Room> findAll(Pageable pageable);
   Page<Room>  find(String nameCity, String nameCategory, Double minAreaRoom,Double maxAreaRoom, String bedsRoom, Double minPriceRoom, Double maxPriceRoom, String bathsRoom, Pageable pageable);
}
