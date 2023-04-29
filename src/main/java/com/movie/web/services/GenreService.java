package com.movie.web.services;

import com.movie.web.dto.GenreDto;

import java.util.List;

public interface GenreService {
    List<GenreDto> findGenresByMovieId(Long movieId);
}
