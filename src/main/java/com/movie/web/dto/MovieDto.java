package com.movie.web.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {
    private Long id;

    @NotEmpty(message = "Название фильма не может быть пустым!")
    private String title;
    @NotEmpty(message = "Описание фильма не может быть пустым!")
    private String description;
    @NotEmpty(message = "Страна выпуска не может быть пустой!")
    private String releaseCountry;
    @NotEmpty(message = "Нет ссылки на обложку фильма!")
    private String imageUrl;
    @Min(value = 1800, message = "Год выпуска не может быть раньше 1800 года!")
    @Max(value = 2030, message = "Год выпуска не может быть позже 2030 года!")
    private int releaseYear;
}
