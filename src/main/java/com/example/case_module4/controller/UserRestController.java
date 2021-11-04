package com.example.case_module4.controller;

import com.example.case_module4.exception.NotFoundException;
import com.example.case_module4.model.Image;
import com.example.case_module4.model.User;
import com.example.case_module4.model.dto.UserForm;
import com.example.case_module4.service.image.IImageService;
import com.example.case_module4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserRestController {

    public static final String NO_RESULTS = "There's no results!";

    @Autowired
    private IUserService userService;

    @Autowired
    private IImageService imageService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(name = "q", required = false)
                                             String keyword,
                                     Pageable pageable) {
        Page<User> userPage;
        if (keyword == null || keyword.equals("")) {
            userPage = userService.findAll(pageable);
        } else {
            userPage = userService.findAllByNameOrPhoneOrEmail(keyword, pageable);
        }
        if (userPage.hasContent()) {
            return new ResponseEntity<>(userPage, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(NO_RESULTS, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) throws NotFoundException {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) throw new NotFoundException();
        return new ResponseEntity<>(userOptional.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserForm userForm) {
        User user = new User();
        if (userForm.getId() != null) {
            user.setId(userForm.getId());
        }
        user.setName(userForm.getName());
        user.setPhone(userForm.getPhone());
        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setAddress(userForm.getAddress());
        user.setRoles(userForm.getRoles());

        MultipartFile image = userForm.getImage();
        String imageName = image.getOriginalFilename() + System.currentTimeMillis();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        imageService.save(new Image(imageName, user));
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody UserForm userForm)
            throws NotFoundException {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) throw new NotFoundException();

        User user = new User();
        if (userForm.getId() != null) {
            user.setId(userForm.getId());
        }
        user.setName(userForm.getName());
        user.setPhone(userForm.getPhone());
        user.setEmail(userForm.getEmail());
        user.setUsername(userForm.getUsername());
        user.setPassword(userForm.getPassword());
        user.setAddress(userForm.getAddress());
        user.setRoles(userForm.getRoles());

        MultipartFile image = userForm.getImage();
        String imageName = image.getOriginalFilename() + System.currentTimeMillis();
        try {
            FileCopyUtils.copy(image.getBytes(), new File(imageName));
        } catch (IOException e) {
            e.printStackTrace();
        }
//        Delete the existing then save the new one

        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUser(@PathVariable Long id) throws NotFoundException {
        Optional<User> userOptional = userService.findById(id);
        if (userOptional.isPresent()) throw new NotFoundException();
//        Delete the image first

        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
