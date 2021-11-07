package com.example.case_module4.controller;

import com.example.case_module4.model.City;
import com.example.case_module4.service.city.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/city")
@CrossOrigin("*")
public class CityRestController {

    @Autowired
    private ICityService cityService;

    @GetMapping()
    public ResponseEntity<Iterable<City>> showAllCity() {
        return new ResponseEntity<>(cityService.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<City> findById(@PathVariable Long id) {

        Optional<City> city = cityService.findById(id);
        return new ResponseEntity<>(city.get(), HttpStatus.OK);
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
    public ResponseEntity<City> delete(@PathVariable Long id) {
        Optional<City> cityOptional = cityService.findById(id);
        if (!cityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            cityService.deleteById(id);
            return new ResponseEntity<>(cityOptional.get(),HttpStatus.OK);
        }
    }

}
