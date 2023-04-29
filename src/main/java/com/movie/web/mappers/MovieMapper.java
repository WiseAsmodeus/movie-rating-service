package com.movie.web.mappers;

import com.movie.web.dto.GenreDto;
import com.movie.web.dto.MovieDto;
import com.movie.web.models.Genre;
import com.movie.web.models.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MovieMapper {
    public static MovieDto mapToMovieDto(Movie movie) {

        return MovieDto.builder()
                .id(movie.getId())
                .imageUrl(movie.getImageUrl())
                .title(movie.getTitle())
                .description(movie.getDescription())
                .releaseCountry(movie.getReleaseCountry())
                .releaseYear(movie.getReleaseYear())
                .genres(movie.getGenres().stream()
                        .map(Genre::getName).collect(Collectors.toList()))
                .build();
    }

    public static Movie mapToMovie(MovieDto movieDto) {
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
