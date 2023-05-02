package com.movie.web.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RatingDto {
    private Long id;

    @Min(value = 0)
    @Max(value = 10)
    private BigDecimal score;

    private Long userId;
    private Long movieId;
}
