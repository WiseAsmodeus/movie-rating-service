package com.movie.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;

    private String title;
    private String text;

    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
}
