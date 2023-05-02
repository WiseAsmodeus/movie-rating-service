package com.movie.web.services.impl;

import com.movie.web.dto.ApplyRatingRequest;
import com.movie.web.models.Rating;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.repositories.RatingRepository;
import com.movie.web.repositories.UserRepository;
import com.movie.web.security.SecurityUtil;
import com.movie.web.services.RatingService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    public void applyRating(ApplyRatingRequest rating) {
        var currentRating = ratingRepository.getRatingByMovie_IdAndUser_Username(
                rating.getMovieId(), rating.getUsername());

        if (currentRating != null) {
            currentRating.setScore(compileRating(rating));
            ratingRepository.save(currentRating);
            return;
        }

        var user = userRepository.findByUsername(SecurityUtil.getSessionUser());
        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        var movie = movieRepository.findById(rating.getMovieId());
        if (movie.isEmpty()) {
            return;
        }

        Rating newRating = Rating.builder()
                .user(user)
                .movie(movie.get())
                .score(compileRating(rating))
                .build();

        ratingRepository.save(newRating);
    }

    @Override
    public BigDecimal countAverageRatingOfMovie(Long movieId) {
        var movie = movieRepository.findById(movieId);

        if (movie.isEmpty()) {
            return null;
        }

        var movieRatings = ratingRepository.findAllByMovieId(movieId);
        var count = movieRatings.size();

        double sum = 0.0;
        for (var rating : movieRatings) {
            sum += rating.getScore().doubleValue();
        }

        double avg = 0.0;
        if (sum != 0) {
            avg = sum / count;
        }

        return new BigDecimal(avg);
    }

    public BigDecimal compileRating(ApplyRatingRequest request) {

        var sum = 0.0;

        sum += request.getActorScore().doubleValue();
        sum += request.getAdviceScore().doubleValue();
        sum += request.getEmotionScore().doubleValue();
        sum += request.getCinemaScore().doubleValue();
        sum += request.getGenreScore().doubleValue();
        sum += request.getOriginalScore().doubleValue();
        sum += request.getLoreScore().doubleValue();
        sum += request.getSenseScore().doubleValue();
        sum += request.getOneBreathScore().doubleValue();
        sum += request.getUnexpectedScore().doubleValue();

        return new BigDecimal(sum);
    }
}
