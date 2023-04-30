package com.movie.web.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="wishlists")
public class Wishlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private UserEntity user;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "wishlists_movies",
            joinColumns = {@JoinColumn(name = "wishlist_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "movie_id", referencedColumnName = "id")}
    )
    private List<Movie> movies;
}
