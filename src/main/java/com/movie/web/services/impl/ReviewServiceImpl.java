package com.movie.web.services.impl;

import com.movie.web.dto.ReviewDto;
import com.movie.web.models.Movie;
import com.movie.web.models.Review;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.repositories.ReviewRepository;
import com.movie.web.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final MovieRepository movieRepository;

    @Override
    public void createReview(Long movieId, ReviewDto reviewDto) {
        Movie movie = movieRepository.findById(movieId).get();

        Review review = mapToReview(reviewDto);
        review.setMovie(movie);

        reviewRepository.save(review);
    }

    public Review mapToReview(ReviewDto reviewDto) {
        return Review.builder()
                .id(reviewDto.getId())
                .createdOn(reviewDto.getCreatedOn())
                .updatedOn(reviewDto.getUpdatedOn())
                .title(reviewDto.getTitle())
                .text(reviewDto.getText())
                .build();
    }
}
