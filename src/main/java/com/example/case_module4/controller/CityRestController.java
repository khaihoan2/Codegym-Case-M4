package com.example.case_module4.controller;

import com.example.case_module4.model.City;
import com.example.case_module4.model.Room;
import com.example.case_module4.service.city.ICityService;
import com.example.case_module4.service.room.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/city")
@CrossOrigin("*")
public class CityRestController {

    @Autowired
    private ICityService cityService;

    @Autowired
    private IRoomService roomService;

    @GetMapping()
    public ResponseEntity<Iterable<City>> showAllCity() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<City> createCity(@RequestBody City city) {
        return new ResponseEntity<>(cityService.save(city), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> editCity(@PathVariable Long id, City city) {
        Optional<City> cityOptional = cityService.findById(id);
        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (city.getId() != null) {
                city.setId(id);
            }
        }
        return createCity(city);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        Optional<City> cityOptional = cityService.findById(id);
        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<Room> rooms = (List<Room>) roomService.findAllByCity(cityOptional.get());
            if (!rooms.isEmpty()) {
                return new ResponseEntity<>("Some room is in this city. Can not delete.", HttpStatus.NO_CONTENT);
            }
            cityService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

}
