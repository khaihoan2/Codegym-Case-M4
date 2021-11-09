package com.example.case_module4.controller;

import com.example.case_module4.model.Role;
import com.example.case_module4.model.User;
import com.example.case_module4.model.constant.RoleName;
import com.example.case_module4.model.dto.JwtResponse;
import com.example.case_module4.service.JwtService;
import com.example.case_module4.service.role.IRoleService;
import com.example.case_module4.service.uploading_file.IUploadingFileService;
import com.example.case_module4.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

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
    public ResponseEntity<User> doRegister(@RequestBody User user) {
        Optional<User> userExist = userService.findByUsername(user.getUsername());
        if (userExist.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findByName(RoleName.ROLE_SELLER));
        roles.add(roleService.findByName(RoleName.ROLE_USER));
        user.setRoles(roles);
        return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
    }



}
