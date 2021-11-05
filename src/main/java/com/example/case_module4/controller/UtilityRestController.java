package com.example.case_module4.controller;

import com.example.case_module4.model.Utility;
import com.example.case_module4.service.utility.IUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/utility")
@CrossOrigin("*")
public class UtilityRestController {
    @Autowired
    private IUtilityService utilityService;

    @GetMapping()
    public ResponseEntity<Iterable<Utility>> showAllUtility() {
        return new ResponseEntity<>(utilityService.findAll(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Utility> createUtility(@RequestBody Utility utility) {
        return new ResponseEntity<>(utilityService.save(utility), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Utility> editUtility(@PathVariable Long id, Utility utility) {
        Optional<Utility> utilityOptional =utilityService.findById(id);
        if (!utilityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            if (utility.getId() != null) {
                utility.setId(id);
            }
        }
        return createUtility(utility);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Utility> delete(@PathVariable Long id) {
        Optional<Utility> utilityOptional = utilityService.findById(id);
        if (!utilityOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            utilityService.deleteById(id);
            return new ResponseEntity<>(utilityOptional.get(),HttpStatus.OK);
        }
    }
}
