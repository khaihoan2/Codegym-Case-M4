package com.example.case_module4.controller;

import com.example.case_module4.model.Image;
import com.example.case_module4.model.User;
import com.example.case_module4.model.dto.JwtResponse;
import com.example.case_module4.model.dto.UserForm;
import com.example.case_module4.service.JwtService;
import com.example.case_module4.service.image.IImageService;
import com.example.case_module4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IImageService imageService;

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtService.generateToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByUsername(user.getUsername()).get();
        return ResponseEntity.ok(
                new JwtResponse(
                        currentUser.getId(),
                        jwt,
                        userDetails.getUsername(),
                        currentUser.getName(),
                        userDetails.getAuthorities()
                )
        );
    }

    @PostMapping("/register")
    public ResponseEntity<User> doRegister(@RequestBody UserForm userForm) {
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
}
