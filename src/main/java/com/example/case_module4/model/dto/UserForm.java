package com.example.case_module4.model.dto;

import com.example.case_module4.model.Role;
import com.example.case_module4.model.User;
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

    private MultipartFile avatar;

    public UserForm() {
    }

    public static User extract(UserForm userForm) {
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
        return user;
    }
}
