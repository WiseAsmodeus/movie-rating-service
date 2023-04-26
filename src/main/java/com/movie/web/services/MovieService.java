package com.movie.web.services;

import com.movie.web.dto.MovieDto;
import com.movie.web.models.Movie;

import java.util.List;

public interface MovieService {
    List<MovieDto> getAll();
    List<MovieDto> searchMovies(String query);

    Movie saveMovie(MovieDto movie);
    MovieDto findMovieById(long movieId);
    void updateMovie(MovieDto movie);
    void deleteMovie(Long movieId);
}
