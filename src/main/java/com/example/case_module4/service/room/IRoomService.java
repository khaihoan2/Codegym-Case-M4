package com.example.case_module4.service.room;

import com.example.case_module4.model.IRoomRating;
import com.example.case_module4.model.Room;
import com.example.case_module4.service.IGeneralService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRoomService extends IGeneralService<Room> {

    Page<IRoomRating> findRoomRating(Pageable pageable);

}
