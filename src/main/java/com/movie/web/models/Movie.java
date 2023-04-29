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

    @ManyToMany
    List<Genre> genres;

    public void addGenre(Genre genre) {
        if (genres == null) {
            genres = new ArrayList<>();
        }

        genres.add(genre);
    }
}
