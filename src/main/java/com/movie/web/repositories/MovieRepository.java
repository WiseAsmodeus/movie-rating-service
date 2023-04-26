package com.movie.web.repositories;

import com.movie.web.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
    @Query("SELECT movie from Movie movie where movie.title like concat('%', :query, '%') ")
    List<Movie> searchMovies(String query);
}
