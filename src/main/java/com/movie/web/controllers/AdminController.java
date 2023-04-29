package com.movie.web.controllers;

import com.movie.web.services.CommentService;
import com.movie.web.services.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AdminController {
    private final MovieService movieService;
    private final CommentService commentService;

    @GetMapping("/admin")
    public String createAdminPage() {
        return "pages/admin/admin-controls";
    }

    @GetMapping("/admin/control-movies")
    public String createControlMoviesPage(Model model) {

        var movies = movieService.getAll();
        model.addAttribute("movies", movies);

        return "pages/admin/admin-movies-control";
    }
}
