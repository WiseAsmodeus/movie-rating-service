package com.movie.web.services;

import com.movie.web.dto.ReviewDto;

public interface ReviewService {
    void createReview(Long movieId, ReviewDto reviewDto);
}
