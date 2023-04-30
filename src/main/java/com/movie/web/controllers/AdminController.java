package com.movie.web.controllers;

import com.movie.web.models.Role;
import com.movie.web.security.SecurityUtil;
import com.movie.web.services.CommentService;
import com.movie.web.services.MovieService;
import com.movie.web.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class AdminController {
    private final UserService userService;
    private final MovieService movieService;
    private final CommentService commentService;

    @GetMapping("/admin")
    public String createAdminPage() {

        var currentUser = userService.getByUsername(SecurityUtil.getSessionUser());
        if (!currentUser.getRoles().stream().map(Role::getName).toList()
                .contains("ADMIN")) {
            return "redirect:/movies";
        }

        return "pages/admin/admin-controls";
    }

    @GetMapping("/admin/control-movies")
    public String createControlMoviesPage(Model model) {

        var movies = movieService.getAll();
        model.addAttribute("movies", movies);

        return "pages/admin/admin-movies-control";
    }
}
