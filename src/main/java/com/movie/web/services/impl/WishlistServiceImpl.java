package com.movie.web.services.impl;

import com.movie.web.dto.WishlistDto;
import com.movie.web.mappers.WishlistMapper;
import com.movie.web.models.Movie;
import com.movie.web.models.Wishlist;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.repositories.UserRepository;
import com.movie.web.repositories.WishlistRepository;
import com.movie.web.services.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;

    @Override
    public void createWishlistForUser(String username) {

        // Check if user's wishlist already exists
        var userWishlist = wishlistRepository.findByUserUsername(username);
        if (userWishlist != null) {
            return;
        }

        var user = userRepository.findByUsername(username);
        List<Movie> movies = new ArrayList<>();

        Wishlist newWishlist = Wishlist.builder()
                .user(user)
                .movies(movies)
                .build();

        wishlistRepository.save(newWishlist);
    }

    @Override
    public WishlistDto findUserWishlist(String username) {

        var userWishlist = wishlistRepository.findByUserUsername(username);
        if (userWishlist == null) {
            createWishlistForUser(username);
        }

        userWishlist = wishlistRepository.findByUserUsername(username);

        return WishlistMapper.mapToWishlistDto(userWishlist);
    }

    @Override
    public void removeFilmFromUserWishlist(Long movieId, String username) {

        var movieToRemove = movieRepository.findById(movieId);
        if (movieToRemove.isEmpty()) {
            return;
        }

        var userWishlist = wishlistRepository.findByUserUsername(username);

        var movies = userWishlist.getMovies();
        if (!movies.contains(movieToRemove.get())) {
            return;
        }

        movies.remove(movieToRemove.get());

        userWishlist.setMovies(movies);

        wishlistRepository.save(userWishlist);
    }

    @Override
    public void addFilmToUserWishlist(Long movieId, String username) {
        var movieToAdd = movieRepository.findById(movieId);
        if (movieToAdd.isEmpty()) {
            return;
        }

        var userWishlist = wishlistRepository.findByUserUsername(username);
        if (userWishlist == null) {
            createWishlistForUser(username);
            userWishlist = wishlistRepository.findByUserUsername(username);
        }

        var movies = userWishlist.getMovies();
        if (movies.contains(movieToAdd.get())) {
            return;
        }

        movies.add(movieToAdd.get());

        userWishlist.setMovies(movies);

        wishlistRepository.save(userWishlist);
    }

    @Override
    public boolean filmExistsInUserWishlist(Long movieId, String username) {
        var movie = movieRepository.findById(movieId);
        if (movie.isEmpty()) {
            return false;
        }

        var userWishlist = wishlistRepository.findByUserUsername(username);
        if (userWishlist == null) {
            return false;
        }

        var movies = userWishlist.getMovies();
        if (movies == null || movies.isEmpty()) {
            return false;
        }

        return movies.contains(movie.get());
    }
}
