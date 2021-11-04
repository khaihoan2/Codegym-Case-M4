package com.example.case_module4.controller;

import com.example.case_module4.model.Room;
import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.dto.RoomForm;
import com.example.case_module4.service.booking.IBookingService;
import com.example.case_module4.service.image.UploadingFileService;
import com.example.case_module4.service.review.IReviewService;
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
    private UploadingFileService uploadingFileService;


    @Value("${file-upload}")
    private String fileUpload;

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
//        room.setAvgRating(roomForm.getAvgRating());
//        room.setAvailable(roomForm.isAvailable());
        MultipartFile[] multipartFiles = roomForm.getFiles();
        for (MultipartFile multipartFile : multipartFiles) {
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
            uploadingFileService.save(new UploadingFile(fileName, room));
        }


        return new ResponseEntity<>(roomService.save(room), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Room> editRoom(@PathVariable Long id, RoomForm roomForm) throws IOException {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Room room = new Room();
            if (roomForm.getId() != null) {
                roomForm.setId(id);
            }
            room.setCategory(roomForm.getCategory());
            room.setHost(roomForm.getHost());
            room.setArea(roomForm.getArea());
            room.setPrice(roomForm.getPrice());
            room.setBeds(roomForm.getBeds());
            room.setBaths(roomForm.getBaths());
            room.setCity(roomForm.getCity());
            room.setAddress(roomForm.getAddress());
            MultipartFile[] multipartFiles = roomForm.getFiles();
            for (MultipartFile multipartFile : multipartFiles) {
                String fileName = multipartFile.getOriginalFilename();
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload + fileName));
                UploadingFile uploadingFile = new UploadingFile();
                uploadingFile.setName(fileName);
                uploadingFile.setRoom(room);
                uploadingFileService.save(uploadingFile);
            }

            roomService.save(room);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id) {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
//            uploadingFileService.deleteById(id);
            roomService.deleteById(id);
        }
        return new ResponseEntity<>(roomOptional.get(), HttpStatus.OK);
    }
}
