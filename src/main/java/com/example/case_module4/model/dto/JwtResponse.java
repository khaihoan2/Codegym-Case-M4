package com.example.case_module4.model.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
public class JwtResponse {
    private Long id;

    private String token;

    private String type = "Bearer";

    private String username;

    private String name;

    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(Long id, String token,
                       String username, String name,
                       Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.name = name;
        this.roles = roles;
    }
}
