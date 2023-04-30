package com.movie.web.controllers;

import com.movie.web.services.WishlistService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/wishlists/{username}")
    public String createUserWishlistPage(
            Model model,
            @PathVariable("username") String username)
    {

        var userWishlist = wishlistService.findUserWishlist(username);
        model.addAttribute("wishlist", userWishlist);

        return "pages/user/user-wishlist";
    }

    @GetMapping("/wishlists/{username}/remove/{movieId}")
    public String removeMovieFromWishlist(
            @PathVariable("username") String username,
            @PathVariable("movieId") Long movieId)
    {
        wishlistService.removeFilmFromUserWishlist(movieId, username);

        return "redirect:/wishlists/" + username;
    }


    @GetMapping("/wishlists/{username}/add/{movieId}")
    public String addMovieToWishlist(
            @PathVariable("username") String username,
            @PathVariable("movieId") Long movieId)
    {
        wishlistService.addFilmToUserWishlist(movieId, username);

        return "redirect:/wishlists/" + username;
    }
}
