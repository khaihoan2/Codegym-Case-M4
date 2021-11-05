package com.example.case_module4.controller;

import com.example.case_module4.model.Subscriber;
import com.example.case_module4.service.subscriber.ISubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/subscriber")
@CrossOrigin("*")
public class SubscriberRestController {
    @Autowired
    private ISubscriberService subscriberService;


    @GetMapping
    public ResponseEntity<Iterable<Subscriber>> showListAll() {
        Iterable<Subscriber> subscribers = subscriberService.findAll();
        return new ResponseEntity<>(subscribers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Subscriber> findById(@PathVariable Long id) {
        Optional<Subscriber> subscriber = subscriberService.findById(id);
        if (!subscriber.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }

    }

    @PostMapping
    public ResponseEntity<Subscriber> createSubscriber(@RequestBody Subscriber subscriber) {
        return new ResponseEntity<>(subscriberService.save(subscriber), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscriber> editSubscriber(@PathVariable Long id, Subscriber subscriber) {
        Optional<Subscriber> subscriberOptional = subscriberService.findById(id);
        if (!subscriberOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (subscriber.getId() != null) {
                subscriber.setId(id);
            }
        }
        return createSubscriber(subscriber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Subscriber> deleteById(@PathVariable Long id) {
        Optional<Subscriber> subscriberOptional = subscriberService.findById(id);
        if (!subscriberOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            subscriberService.deleteById(id);
            return new ResponseEntity<>(subscriberOptional.get(),HttpStatus.OK);
        }
    }
}
