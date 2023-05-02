package com.movie.web.services;

import com.movie.web.dto.ApplyRatingRequest;

import java.math.BigDecimal;

public interface RatingService {
    void applyRating(ApplyRatingRequest rating);
    BigDecimal countAverageRatingOfMovie(Long movieId);
}
