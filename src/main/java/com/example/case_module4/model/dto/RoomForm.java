package com.example.case_module4.model.dto;

import com.example.case_module4.model.Category;
import com.example.case_module4.model.City;
import com.example.case_module4.model.User;
import org.springframework.web.multipart.MultipartFile;

public class RoomForm {
    private Long id;

    private Category category;

    private User host;

    private double area;

    private double price;

    private int beds;

    private int baths;

    private City city;

    private String address;

    private double avgRating;

    private boolean isAvailable;

    private MultipartFile[] files;
}
