package com.example.case_module4.controller;

import com.example.case_module4.model.IRoomRating;
import com.example.case_module4.model.Room;
import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.dto.RoomForm;
import com.example.case_module4.service.room.IRoomService;
import com.example.case_module4.service.uploading_file.IUploadingFileService;
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
@CrossOrigin("*")
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
        Room room = RoomForm.extract(roomForm);

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

        Room room = RoomForm.extract(roomForm);

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

    @GetMapping("/rating")
    public ResponseEntity<Page<IRoomRating>> findRomeRating(@PageableDefault(size = 10) Pageable pageable){
        Page<IRoomRating> roomRatings = roomService.findRoomRating(pageable);
        return new ResponseEntity<>(roomRatings, HttpStatus.OK);
    }

    @GetMapping("/findRoom")
    public ResponseEntity<?> findRoom(@RequestParam(name = "cityId", required = false) Long cityId,
                                      @RequestParam(name = "categoryId", required = false) Long categoryId,
                                      @RequestParam(name = "minAreaRoom", required = false) Double minAreaRoom,
                                      @RequestParam(name = "maxAreaRoom", required = false) Double maxAreaRoom,
                                      @RequestParam(name = "bedsRoom", required = false) Long bedsRoom,
                                      @RequestParam(name = "minPriceRoom", required = false) Double minPriceRoom,
                                      @RequestParam(name = "maxPriceRoom", required = false) Double maxPriceRoom,
                                      @RequestParam(name = "bathsRoom", required = false) Long bathsRoom,
                                      @RequestParam(name = "page", required = false) int page) {
        int size = 6;
        String city = "";
        if (cityId != null) {
            city = String.valueOf(cityId);
        }
        String category = "";
        if (categoryId != null) {
            category = String.valueOf(categoryId);
        }
        String beds = "";
        if (bedsRoom != null) {
            beds = String.valueOf(bedsRoom);
        }
        String baths = "";
        if (bathsRoom != null) {
            baths = String.valueOf(bathsRoom);
        }
        int position = page * size;
        Iterable<Room> rooms =  roomService.findRoom(city, category, minAreaRoom, maxAreaRoom, beds, minPriceRoom, maxPriceRoom, baths, size, position);
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
