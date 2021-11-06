package com.example.case_module4.controller;

import com.example.case_module4.exception.NotFoundException;
import com.example.case_module4.model.Review;
import com.example.case_module4.model.UploadingFile;
import com.example.case_module4.model.User;
import com.example.case_module4.model.dto.UserForm;
import com.example.case_module4.service.uploading_file.IUploadingFileService;
import com.example.case_module4.service.review.IReviewService;
import com.example.case_module4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@CrossOrigin("*")
public class UserRestController {

    public static final String NO_RESULTS = "There's no results!";

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUploadingFileService uploadingFileService;

    @Autowired
    private IReviewService reviewService;

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
        User user = UserForm.extract(userForm);

        MultipartFile uploadingFile = userForm.getImage();
        String fileName = uploadingFile.getOriginalFilename() + System.currentTimeMillis();
        try {
            FileCopyUtils.copy(uploadingFile.getBytes(), new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        uploadingFileService.save(new UploadingFile(fileName, user));
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody UserForm userForm)
            throws NotFoundException {
        Optional<User> userOptional = userService.findById(id);
        if (!userOptional.isPresent()) throw new NotFoundException();

        User user = UserForm.extract(userForm);

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

//        Delete the image in the database
        UploadingFile uploadingFile = uploadingFileService.findByUser(userOptional.get()).get();
        uploadingFileService.deleteById(uploadingFile.getId());
//        Delete the archive folder File image .
        new File(fileUpload + uploadingFile.getName()).delete();
        //-------------------------------------//
//        Delete Review in the database
        Review review = reviewService.findByUser(userOptional.get()).get();
        reviewService.deleteById(review.getId());

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
