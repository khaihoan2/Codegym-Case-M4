package com.example.case_module4.controller;

import com.example.case_module4.model.IAvgReviewAndCount;
import com.example.case_module4.model.Review;
import com.example.case_module4.service.JwtService;
import com.example.case_module4.service.review.IReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/reviews")
@CrossOrigin("*")
public class ReviewRestController {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IReviewService reviewService;

    @GetMapping
    public ResponseEntity<Iterable<Review>> showListAll() {
        Iterable<Review> reviews = reviewService.findAll();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> findById(@PathVariable Long id) {
        Optional<Review> reviewOptional = reviewService.findById(id);
        if (!reviewOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviewOptional.get(), HttpStatus.OK);
    }

    @GetMapping("/rating/{id}")
    public ResponseEntity<IAvgReviewAndCount> avgReview(@PathVariable Long id) {
        IAvgReviewAndCount avgReviewAndCount = reviewService.avgReviewAndCount(id);
        return new ResponseEntity<>(avgReviewAndCount, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createReview(@RequestBody Review review) {
            return new ResponseEntity<>(reviewService.save(review), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Review review,
                                               @RequestBody Long id) {
        Optional<Review> reviewOptional = reviewService.findById(id);
        if (!reviewOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        review.setId(id);
        return new ResponseEntity<>(reviewService.save(review), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Review> deleteReview(@PathVariable Long id) {
        Optional<Review> reviewOptional = reviewService.findById(id);
        if (!reviewOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reviewOptional.get(), HttpStatus.NO_CONTENT);
    }
}
