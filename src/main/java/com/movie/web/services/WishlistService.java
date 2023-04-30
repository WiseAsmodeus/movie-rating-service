package com.movie.web.services;

import com.movie.web.dto.WishlistDto;

public interface WishlistService {
    void createWishlistForUser(String username);
    WishlistDto findUserWishlist(String username);

    void removeFilmFromUserWishlist(Long movieId, String username);
    void addFilmToUserWishlist(Long movieId, String username);
    boolean filmExistsInUserWishlist(Long movieId, String username);
}
