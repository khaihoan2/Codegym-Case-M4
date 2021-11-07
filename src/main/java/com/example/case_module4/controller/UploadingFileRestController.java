package com.example.case_module4.controller;


import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.service.image.IUploadingFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/images")
@CrossOrigin("*")
public class UploadingFileRestController {

    @Autowired
    private IUploadingFileService uploadingFileService;

    @GetMapping
    public ResponseEntity<Iterable<UploadingFile>> showListAll() {
        Iterable<UploadingFile> uploadingFiles = uploadingFileService.findAll();
        return new ResponseEntity<>(uploadingFiles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UploadingFile> findById(@PathVariable Long id) {
        Optional<UploadingFile> uploadingFileOptional = uploadingFileService.findById(id);
        if (!uploadingFileOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<Iterable<UploadingFile>> findByRoomId(@PathVariable Long id) {
        Iterable<UploadingFile> files = uploadingFileService.findByRoomId(id);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Iterable<UploadingFile>> findByUserId(@PathVariable Long id) {
        Iterable<UploadingFile> files = uploadingFileService.findByUserId(id);
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UploadingFile> createImage(@RequestBody UploadingFile uploadingFile) {
        return new ResponseEntity<>(uploadingFileService.save(uploadingFile), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UploadingFile> updateImage(@PathVariable Long id,
                                                     @RequestBody UploadingFile uploadingFile) {
        Optional<UploadingFile> imageOptional = uploadingFileService.findById(id);
        if (!imageOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        uploadingFile.setId(id);
        return new ResponseEntity<>(uploadingFileService.save(uploadingFile), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UploadingFile> deleteImage(@PathVariable Long id) {
        Optional<UploadingFile> imageOptional = uploadingFileService.findById(id);
        if (!imageOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        uploadingFileService.deleteById(id);
        return new ResponseEntity<>(imageOptional.get(), HttpStatus.NO_CONTENT);
    }
}
