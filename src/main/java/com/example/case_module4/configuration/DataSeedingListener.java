package com.example.case_module4.configuration;

import com.example.case_module4.model.*;
import com.example.case_module4.model.constant.CategoryName;
import com.example.case_module4.model.constant.CityName;
import com.example.case_module4.model.constant.RoleName;
import com.example.case_module4.repository.ICategoryRepository;
import com.example.case_module4.repository.ICityRepository;
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
    private ICityRepository cityRepository;

    @Autowired
    private ICategoryRepository categoryRepository;

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

        // Cities
        if (cityRepository.findByName(CityName.CITY_HANOI) == null) {
            cityRepository.save(new City(CityName.CITY_HANOI));
        }
        if (cityRepository.findByName(CityName.CITY_PARIS) == null) {
            cityRepository.save(new City(CityName.CITY_PARIS));
        }
        if (cityRepository.findByName(CityName.CITY_LONDON) == null) {
            cityRepository.save(new City(CityName.CITY_LONDON));
        }

        // Categories
        if (categoryRepository.findByName(CategoryName.CATEGORY_CONDO) == null) {
            categoryRepository.save(new Category(CategoryName.CATEGORY_CONDO));
        }
        if (categoryRepository.findByName(CategoryName.CATEGORY_VILLA) == null) {
            categoryRepository.save(new Category(CategoryName.CATEGORY_VILLA));
        }
        if (categoryRepository.findByName(CategoryName.CATEGORY_HOMESTAY) == null) {
            categoryRepository.save(new Category(CategoryName.CATEGORY_HOMESTAY));
        }
        if (categoryRepository.findByName(CategoryName.CATEGORY_APARTMENT) == null) {
            categoryRepository.save(new Category(CategoryName.CATEGORY_APARTMENT));
        }

        // Default administrator
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

        //Default seller
        if (!userRepository.findByUsername("seller").isPresent()) {
            User user = new User();
            user.setName("seller");
            user.setPhone("0396520067");
            user.setEmail("seller@gmail.com");
            user.setUsername("seller");
            user.setPassword(passwordEncoder.encode("seller"));
            user.setAddress("on the Jupiter");
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_SELLER));
            roles.add(roleRepository.findByName(RoleName.ROLE_USER));
            user.setRoles(roles);
            userRepository.save(user);
        }

        //Default user
        if (!userRepository.findByUsername("user").isPresent()) {
            User user = new User();
            user.setName("user");
            user.setPhone("19001798");
            user.setEmail("user@gmail.com");
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user"));
            user.setAddress("on the Moon");
            Set<Role> roles = new HashSet<>();
            roles.add(roleRepository.findByName(RoleName.ROLE_USER));
            user.setRoles(roles);
            userRepository.save(user);
        }
    }
}
