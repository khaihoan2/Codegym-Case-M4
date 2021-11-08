package com.example.case_module4.service.room;

import com.example.case_module4.model.Category;
import com.example.case_module4.model.City;
import com.example.case_module4.model.IRoomRating;
import com.example.case_module4.model.Room;
import com.example.case_module4.repository.IRoomRepository;
import com.example.case_module4.service.booking.IBookingService;
import com.example.case_module4.service.review.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IReviewService reviewService;

    @Autowired
    private IBookingService bookingService;

    @Override
    public Iterable<Room> findAll() {
        Iterable<Room> rooms = roomRepository.findAll();
        for (Room room : rooms) {
            Boolean isAvailable = bookingService.checkRoomAvailable(room.getId());
            Long avgId = reviewService.avgReview(room.getId());
//            room.setAvgRating(avgId);
            room.setIsAvailable(isAvailable);
        }
        return roomRepository.findAll();
    }

    @Override
    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public Room save(Room room) {
        return roomRepository.save(room);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Page<IRoomRating> findRoomRating(Pageable pageable) {
        return roomRepository.findRoomRating(pageable);
    }

    @Override
    public Page<Room> findAll(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public Iterable<Room> findAllByCategory(Category category) {
        return roomRepository.findAllByCategory(category);
    }

    @Override
    public Iterable<Room> findAllByCity(City city) {
        return null;
    }

    @Override
    public Iterable<Room> findRoom(String nameCity, String nameCategory, Double minAreaRoom, Double maxAreaRoom, String bedsRoom, Double minPriceRoom, Double maxPriceRoom, String bathsRoom, int size, int position) {
        return roomRepository.find_room(nameCity,nameCategory,minAreaRoom,maxAreaRoom,bedsRoom,minPriceRoom,maxPriceRoom,bathsRoom, size, position);
    }


}
