package com.example.case_module4.controller;


import com.example.case_module4.model.Image;
import com.example.case_module4.service.image.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    @Autowired
    private IImageService imageService;

    @GetMapping
    public ResponseEntity<Iterable<Image>> showListAll() {
        Iterable<Image> images = imageService.findAll();
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> findById(@PathVariable Long id) {
        Optional<Image> imageOptional = imageService.findById(id);
        if (!imageOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PostMapping
    public ResponseEntity<Image> createImage(@RequestBody Image image) {
        return new ResponseEntity<>(imageService.save(image), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> updateImage(@PathVariable Long id,
                                             @RequestBody Image image) {
        Optional<Image> imageOptional = imageService.findById(id);
        if (!imageOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        image.setId(id);
        return new ResponseEntity<>(imageService.save(image), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Image> deleteImage(@PathVariable Long id) {
        Optional<Image> imageOptional = imageService.findById(id);
        if (!imageOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        imageService.deleteById(id);
        return new ResponseEntity<>(imageOptional.get(), HttpStatus.NO_CONTENT);
    }
}
