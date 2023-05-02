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
import com.movie.web.services.RatingService;
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
    private final RatingService ratingService;

    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final CommentRepository commentRepository;

    @Override
    public List<MovieDto> getAll() {
        var movies = movieRepository.findAll();

        var movieDtoList = movies.stream()
                .map(MovieMapper::mapToMovieDto)
                .collect(Collectors.toList());

        applyRatings(movieDtoList);

        return movieDtoList;
    }

    @Override
    public List<MovieDto> searchMovies(String query) {
        List<Movie> movies = movieRepository.searchMovies(query);
        var filteredMovies = movies.stream()
                .map(MovieMapper::mapToMovieDto)
                .collect(Collectors.toList());

        applyRatings(filteredMovies);

        return filteredMovies;
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
        var movieDto = mapToMovieDto(movie);

        applyRating(movieId, movieDto);

        return movieDto;
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

        var movies = movieRepository.findMoviesByGenres_Id(genreId)
                .stream().map(MovieMapper::mapToMovieDto)
                .collect(Collectors.toList());

        applyRatings(movies);

        return movies;
    }

    @Override
    public void applyRatings(List<MovieDto> movies) {
        for (MovieDto movie : movies) {
            applyRating(movie.getId(), movie);
        }
    }

    private void applyRating(long movieId, MovieDto movieDto) {
        var rating = ratingService.countAverageRatingOfMovie(movieId)
                .doubleValue();

        movieDto.setRating(String.format("%,.2f", rating));
    }
}
