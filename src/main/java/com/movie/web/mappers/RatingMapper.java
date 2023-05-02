package com.movie.web.mappers;

import com.movie.web.dto.RatingDto;
import com.movie.web.models.Rating;

public class RatingMapper {
    public static RatingDto mapToRatingDto(Rating rating) {
        return RatingDto.builder()
                .id(rating.getId())
                .score(rating.getScore())
                .userId(rating.getUser().getId())
                .movieId(rating.getUser().getId())
                .build();
    }
}
