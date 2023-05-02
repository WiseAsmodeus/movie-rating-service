package com.movie.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplyRatingRequest {
    private String username;
    private Long movieId;

    private BigDecimal loreScore;
    private BigDecimal actorScore;
    private BigDecimal cinemaScore;
    private BigDecimal genreScore;
    private BigDecimal originalScore;
    private BigDecimal oneBreathScore;
    private BigDecimal unexpectedScore;
    private BigDecimal emotionScore;
    private BigDecimal adviceScore;
    private BigDecimal senseScore;
}
