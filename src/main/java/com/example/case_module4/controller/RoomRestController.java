package com.example.case_module4.controller;

import com.example.case_module4.model.Room;
import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.dto.RoomForm;
import com.example.case_module4.service.image.IUploadingFileService;
import com.example.case_module4.service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/rooms")
public class RoomRestController {


    @Autowired
    private IRoomService roomService;

    @Autowired
    private IUploadingFileService uploadingFileService;


    @Value("${file-upload}")
    private String fileUpload;
//
//    @Autowired
//    private ICategoryService categoryService;


//    @GetMapping()
//    public ResponseEntity<?> showAllRoom() {
//        Iterable<Room> rooms = roomService.findAll();
//        return new ResponseEntity<>(rooms, HttpStatus.OK);
//    }

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
    public ResponseEntity<Room> createRoom(@RequestBody RoomForm roomForm) {
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
    public ResponseEntity<Room> editRoom(@PathVariable Long id,
                                         @RequestBody RoomForm roomForm) throws IOException {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> deleteRoom(@PathVariable Long id) {
        Optional<Room> roomOptional = roomService.findById(id);
        if (!roomOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        roomService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/findRoom")
    public ResponseEntity<?> findRoom(@RequestParam(name = "nameCity", required = false) String nameCity,
                                      @RequestParam(name = "nameCategory", required = false) String nameCategory,
                                      @RequestParam(name = "aresRoom", required = false) double aresRoom,
                                      @RequestParam(name = "bedsRoom", required = false) int bedsRoom,
                                      @RequestParam(name = "priceRoom", required = false) double priceRoom,
                                      @RequestParam(name = "bathsRoom", required = false) int bathsRoom,
                                      @PageableDefault(size = 3) Pageable pageable) {
        Page<Room> rooms;
        if (nameCity == null || nameCity.equals("") ||
                nameCategory == null || nameCategory.equals("")) {
            rooms = roomService.findAll(pageable);
        } else {
            rooms = roomService.find(nameCity, nameCategory, aresRoom, bedsRoom, priceRoom, bathsRoom, pageable);
        }

        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
