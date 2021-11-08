package com.example.case_module4.repository;

import com.example.case_module4.model.IAvgReviewAndCount;
import com.example.case_module4.model.Review;
import com.example.case_module4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IReviewRepository extends JpaRepository<Review, Long> {
    @Query(value = "call avgReview (?1)",nativeQuery = true)
    Long avgReview(Long id);

    @Query(value = "call avg_review_and_count(?1)", nativeQuery = true)
    IAvgReviewAndCount avgReviewAndCount(Long id);


    Iterable<Review> findByAuthor(User author);
}
