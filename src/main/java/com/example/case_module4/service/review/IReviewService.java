package com.example.case_module4.service.review;

import com.example.case_module4.model.IAvgReviewAndCount;
import com.example.case_module4.model.Review;
import com.example.case_module4.model.User;
import com.example.case_module4.service.IGeneralService;

import java.util.List;
import java.util.Optional;

public interface IReviewService extends IGeneralService<Review> {
    Long avgReview(Long id);

    IAvgReviewAndCount avgReviewAndCount(Long id);

    Iterable<Review> findByAuthor(User author);

}
