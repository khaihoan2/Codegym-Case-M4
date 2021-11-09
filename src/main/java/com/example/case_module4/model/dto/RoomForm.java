package com.example.case_module4.model.dto;

import com.example.case_module4.model.Category;
import com.example.case_module4.model.City;
import com.example.case_module4.model.Room;
import com.example.case_module4.model.User;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RoomForm {
    private Long id;

    private String name;

    private Category category;

    private User host;

    private double area;

    private double price;

    private int beds;

    private int baths;

    private City city;

    private String address;

    private boolean isAvailable;

    private MultipartFile[] files;

    public static Room extract(RoomForm roomForm) {
        Room room = new Room();
        if (roomForm.getId() != null) {
            room.setId(roomForm.getId());
        }
        room.setName(roomForm.getName());
        room.setCategory(roomForm.getCategory());
        room.setHost(roomForm.getHost());
        room.setArea(roomForm.getArea());
        room.setPrice(roomForm.getPrice());
        room.setBeds(roomForm.getBeds());
        room.setBaths(roomForm.getBaths());
        room.setCity(roomForm.getCity());
        room.setAddress(roomForm.getAddress());
        room.setIsAvailable(roomForm.isAvailable());
        return room;
    }
}
