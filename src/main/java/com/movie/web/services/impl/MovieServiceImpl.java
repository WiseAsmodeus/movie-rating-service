package com.movie.web.services.impl;

import com.movie.web.dto.MovieDto;
import com.movie.web.models.Movie;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<MovieDto> getAll() {
        var movies = movieRepository.findAll();

        return movies.stream()
                .map(this::mapToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> searchMovies(String query) {
        List<Movie> movies = movieRepository.searchMovies(query);
        return movies.stream()
                .map(this::mapToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public Movie saveMovie(MovieDto movieDto) {
        var movie = mapToMovie(movieDto);
        return movieRepository.save(movie);
    }

    @Override
    public MovieDto findMovieById(long movieId) {
        var movie = movieRepository.findById(movieId).get();

        return mapToMovieDto(movie);
    }

    @Override
    public void updateMovie(MovieDto movie) {
        saveMovie(movie);
    }

    @Override
    public void deleteMovie(Long movieId) {
        movieRepository.deleteById(movieId);
    }

    public MovieDto mapToMovieDto(Movie movie) {
        return MovieDto.builder()
                .id(movie.getId())
                .imageUrl(movie.getImageUrl())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseCountry(movie.getReleaseCountry())
                .releaseYear(movie.getReleaseYear())
                .build();
    }

    public Movie mapToMovie(MovieDto movieDto) {
        return Movie.builder()
                .id(movieDto.getId())
                .imageUrl(movieDto.getImageUrl())
                .title(movieDto.getTitle())
                .description(movieDto.getDescription())
                .releaseCountry(movieDto.getReleaseCountry())
                .releaseYear(movieDto.getReleaseYear())
                .build();
    }
}
