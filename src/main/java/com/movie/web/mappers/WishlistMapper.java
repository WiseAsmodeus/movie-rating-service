package com.movie.web.mappers;


import com.movie.web.dto.WishlistDto;
import com.movie.web.models.Wishlist;

import java.util.stream.Collectors;

public class WishlistMapper {

    public static WishlistDto mapToWishlistDto(Wishlist wishlist) {
        return WishlistDto.builder()
                .id(wishlist.getId())
                .movieDtoList(wishlist.getMovies().stream()
                        .map(MovieMapper::mapToMovieDto)
                        .collect(Collectors.toList()))
                .username(wishlist.getUser().getUsername())
                .build();
    }

}
