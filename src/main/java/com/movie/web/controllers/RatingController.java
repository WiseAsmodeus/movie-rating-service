package com.movie.web.controllers;

import com.movie.web.dto.ApplyRatingRequest;
import com.movie.web.dto.RatingDto;
import com.movie.web.security.SecurityUtil;
import com.movie.web.services.MovieService;
import com.movie.web.services.RatingService;
import com.movie.web.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/movies/{movieId}/rating-helper")
    public String createRatingHelperPage(
            @PathVariable("movieId") Long movieId,
            Model model
    ) {

        ApplyRatingRequest ratingDto = ApplyRatingRequest.builder()
                .movieId(movieId)
                .build();
        model.addAttribute("ratingModal", ratingDto);

        return "pages/movies/apply-rating";
    }

    @PostMapping("/movies/{movieId}/apply")
    public String applyRating(
            @ModelAttribute("ratingModal") ApplyRatingRequest request,
            @PathVariable("movieId") Long movieId)
    {
        ratingService.applyRating(request);

        return "redirect:/movies/" + movieId;
    }
}
