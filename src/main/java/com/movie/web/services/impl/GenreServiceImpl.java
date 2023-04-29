package com.movie.web.services.impl;

import com.movie.web.dto.GenreDto;
import com.movie.web.mappers.GenreMapper;
import com.movie.web.repositories.GenreRepository;
import com.movie.web.services.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> findGenresByMovieId(Long movieId) {
        return genreRepository.findGenresByMovies_Id(movieId)
                .stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreDto> getAllGenres() {
        return genreRepository.findAll()
                .stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GenreDto> getGenresByNames(List<String> genreNames) {
        List<GenreDto> genres = new ArrayList<>();

        for (var genreName : genreNames) {
            var foundGenre = genreRepository.getGenreByName(genreName);
            genres.add(GenreMapper.mapToGenreDto(foundGenre));
        }

        return genres;
    }
}
