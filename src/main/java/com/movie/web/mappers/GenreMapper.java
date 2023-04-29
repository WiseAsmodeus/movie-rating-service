package com.movie.web.mappers;

import com.movie.web.dto.GenreDto;
import com.movie.web.models.Genre;

public class GenreMapper {
    public static Genre mapToGenre(GenreDto genreDto) {
        return Genre.builder()
                .id(genreDto.getId())
                .name(genreDto.getName())
                .build();
    }

    public static GenreDto mapToGenreDto(Genre genre) {
        return GenreDto.builder()
                .id(genre.getId())
                .name(genre.getName())
                .build();
    }
}
