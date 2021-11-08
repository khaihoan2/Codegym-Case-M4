package com.example.case_module4.service.review;

import com.example.case_module4.model.IAvgReviewAndCount;
import com.example.case_module4.model.Review;
import com.example.case_module4.model.User;
import com.example.case_module4.repository.IReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService implements IReviewService {

    @Autowired
    private IReviewRepository reviewRepository;

    @Override
    public Iterable<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review save(Review review) {
        return reviewRepository.save(review);
    }

    @Override
    public void deleteById(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public Long avgReview(Long id) {
        return reviewRepository.avgReview(id);
    }

    @Override
    public IAvgReviewAndCount avgReviewAndCount(Long id) {
        return reviewRepository.avgReviewAndCount(id);
    }

    @Override
    public Optional<Review> findByUser(User user) {
        return null;
    }

}
