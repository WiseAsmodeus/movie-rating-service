package com.movie.web.repositories;

import com.movie.web.models.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    boolean existsRatingByMovie_IdAndUser_Id(Long movieId, Long userId);
    Rating getRatingByMovie_IdAndUser_Username(Long movieId, String username);
    List<Rating> findAllByMovieId(Long movieId);
}
