package com.example.case_module4.repository;

import com.example.case_module4.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReviewRepository extends JpaRepository<Review, Long> {
}
