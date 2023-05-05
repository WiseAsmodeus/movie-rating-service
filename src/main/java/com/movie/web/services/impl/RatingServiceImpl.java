package com.movie.web.services.impl;

import com.movie.web.dto.ApplyRatingRequest;
import com.movie.web.models.Rating;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.repositories.RatingRepository;
import com.movie.web.repositories.UserRepository;
import com.movie.web.security.SecurityUtil;
import com.movie.web.services.RatingService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

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

    @Override
    @Transactional
    public List<List<Object>> rangeRatings(Long movieId) {

        var movieRatings = ratingRepository.findAllByMovieId(movieId);
        if (movieRatings == null || movieRatings.isEmpty()) {
            return new ArrayList<>();
        }

        Dictionary<Integer, List<Object>> rangedRatings = new Hashtable<>();
        for (int i = 0; i < 10; i++) {
            rangedRatings.put(i, new ArrayList<>());
        }

        for (var rating : movieRatings) {
            var score = rating.getScore().doubleValue();
            var key = (int)Math.floor(score);
            if (key == 10) {
                key--;
            }
            rangedRatings.get(key).add(score);
        }

        List<List<Object>> result = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (rangedRatings.get(i).isEmpty()) {
                continue;
            }

            var title = String.format("От %d до %d", i, i+1);
            result.add(List.of(title, rangedRatings.get(i).size()));
        }

        return result;
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
