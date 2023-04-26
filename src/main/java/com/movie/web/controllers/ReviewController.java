package com.movie.web.controllers;

import com.movie.web.models.Review;
import com.movie.web.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/{movieId}/new")
    public String createReviewForm(@PathVariable("movieId") Long movieId, Model model) {

        Review review = new Review();
        model.addAttribute("review", review);
        model.addAttribute("movieId", movieId);

        return "reviews-create";
    }
}
