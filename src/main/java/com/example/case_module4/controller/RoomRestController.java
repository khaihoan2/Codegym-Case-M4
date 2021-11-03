package com.example.case_module4.controller;

import com.example.case_module4.model.Category;
import com.example.case_module4.model.Image;
import com.example.case_module4.model.Room;
import com.example.case_module4.model.dto.RoomForm;
import com.example.case_module4.service.category.ICategoryService;
import com.example.case_module4.service.image.ImageService;
import com.example.case_module4.service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/room")
public class RoomRestController {


    @Autowired
    private IRoomService roomService;

    @Autowired
    private ImageService imageService;


    @Value("${file-upload}")
    private String fileUpload;
//
//    @Autowired
//    private ICategoryService categoryService;

    @GetMapping()
    public ResponseEntity<Iterable<Room>> showAllRoom() {
        Iterable<Room> rooms = roomService.findAll();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable Long id) {
        Optional<Room> room = roomService.findById(id);
        if (!room.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(room.get(), HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Room> createRoom(RoomForm roomForm) {
        MultipartFile[] multipartFiles = roomForm.getFiles();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }

            imageService.save(new Image(fileName));
        }
        Room room = new Room();
        if (roomForm.getId() != null) {
            room.setId(roomForm.getId());
        }
        room.setCategory(roomForm.getCategory());
        room.setHost(roomForm.getHost());
        room.setArea(roomForm.getArea());
        room.setPrice(roomForm.getPrice());
        room.setBeds(roomForm.getBeds());
        room.setBaths(roomForm.getBaths());
        room.setCity(roomForm.getCity());
        room.setAddress(roomForm.getAddress());
        room.setAvgRating(roomForm.getAvgRating());
        room.setAvailable(roomForm.isAvailable());
        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> editRoom(@PathVariable Long id, RoomForm roomForm) {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (roomForm.getId() != null) {
                roomForm.setId(id);
            }

        }
        return createRoom(roomForm);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id) {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            roomService.deleteById(id);
        }
        return new ResponseEntity<>(roomOptional.get(), HttpStatus.OK);
    }
}
