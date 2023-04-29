package com.movie.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies_genres")
public class MoviesGenres {
    @Id
    @Basic
    @Column(name = "movie_id")
    private Long movieId;
    @Id
    @Basic
    @Column(name = "genre_id")
    private Long genreId;
}
