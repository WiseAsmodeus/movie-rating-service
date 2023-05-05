package com.movie.web.services;

import com.movie.web.dto.ApplyRatingRequest;

import java.math.BigDecimal;
import java.util.List;

public interface RatingService {
    void applyRating(ApplyRatingRequest rating);
    BigDecimal countAverageRatingOfMovie(Long movieId);

    List<List<Object>> rangeRatings(Long movieId);
}
