package com.example.case_module4.model.dto;

import com.example.case_module4.model.Role;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;

@Data
public class UserForm {
    private Long id;

    private String name;

    private String phone;

    private String email;

    private String username;

    private String password;

    private String address;

    private Collection<Role> roles;

    private MultipartFile image;

    public UserForm() {
    }
}
