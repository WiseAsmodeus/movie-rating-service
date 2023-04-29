package com.movie.web.services.impl;

import com.movie.web.dto.MovieDto;
import com.movie.web.mappers.MovieMapper;
import com.movie.web.models.Movie;
import com.movie.web.repositories.GenreRepository;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.movie.web.mappers.MovieMapper.mapToMovie;
import static com.movie.web.mappers.MovieMapper.mapToMovieDto;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;

    @Override
    public List<MovieDto> getAll() {
        var movies = movieRepository.findAll();

        return movies.stream()
                .map(MovieMapper::mapToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MovieDto> searchMovies(String query) {
        List<Movie> movies = movieRepository.searchMovies(query);
        return movies.stream()
                .map(MovieMapper::mapToMovieDto)
                .collect(Collectors.toList());
    }

    @Override
    public void saveMovie(MovieDto movieDto) {
        var movie = mapToMovie(movieDto);
        movieRepository.save(movie);
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

    @Override
    public List<MovieDto> findMoviesByGenreName(String genreName) {
        var genreId = genreRepository.getGenreByName(genreName).getId();

        return movieRepository.findMoviesByGenres_Id(genreId)
                .stream().map(MovieMapper::mapToMovieDto)
                .collect(Collectors.toList());
    }


}
