package com.movie.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;
    private String releaseCountry;
    private String description;
    private String imageUrl;

    private int releaseYear;

    @OneToMany
    private List<Comment> comments;

    @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(name = "movies_genres",
            joinColumns = {
                @JoinColumn(name = "movie_id", referencedColumnName = "id")
            },
            inverseJoinColumns = {
                @JoinColumn(name = "genre_id", referencedColumnName = "id")
            })
    private List<Genre> genres;
}
