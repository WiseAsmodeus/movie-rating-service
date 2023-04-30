package com.movie.web.services.impl;

import com.movie.web.dto.MovieDto;
import com.movie.web.mappers.GenreMapper;
import com.movie.web.mappers.MovieMapper;
import com.movie.web.models.Movie;
import com.movie.web.repositories.CommentRepository;
import com.movie.web.repositories.GenreRepository;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.services.GenreService;
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

    private final GenreService genreService;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

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

        System.out.println(movie.getId() + "  " + movie.toString());

        var genres = genreService.getGenresByNames(movieDto.getGenres())
                .stream().map(GenreMapper::mapToGenre).collect(Collectors.toList());
        movie.setGenres(genres);

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
