package com.example.case_module4.repository;

import com.example.case_module4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Override
    Page<User> findAll(Pageable pageable);

    Page<User> findAllByNameContainingOrPhoneContainingOrEmailContaining(
            String nameKeyword,
            String phoneKeyword,
            String emailKeyword,
            Pageable pageable
    );
}
