package com.example.case_module4.configuration;

import com.example.case_module4.model.Role;
import com.example.case_module4.model.User;
import com.example.case_module4.model.constant.RoleName;
import com.example.case_module4.repository.IRoleRepository;
import com.example.case_module4.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // Roles
        if (roleRepository.findByName(RoleName.ROLE_ADMIN) == null) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
        }
        if (roleRepository.findByName(RoleName.ROLE_SELLER) == null) {
            roleRepository.save(new Role(RoleName.ROLE_SELLER));
        }
        if (roleRepository.findByName(RoleName.ROLE_USER) == null) {
            roleRepository.save(new Role(RoleName.ROLE_USER));
        }

        // Default admin user
        if (!userRepository.findByUsername("admin").isPresent()) {
            User user = new User();
            user.setName("admin");
            user.setPhone("0907128338");
            user.setEmail("admin@gmail.com");
            user.setUsername("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setAddress("on the Mars");
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_ADMIN));
            roles.add(roleRepository.findByName(RoleName.ROLE_SELLER));
            roles.add(roleRepository.findByName(RoleName.ROLE_USER));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
