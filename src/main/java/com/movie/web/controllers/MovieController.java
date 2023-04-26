package com.movie.web.controllers;

import com.movie.web.dto.MovieDto;
import com.movie.web.models.Movie;
import com.movie.web.services.MovieService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping
public class  MovieController {

    private final MovieService movieService;

    @GetMapping("/movies")
    public String listMovies(Model model) {

        var movies = movieService.getAll();
        model.addAttribute("movies", movies);

        return "movies-list";
    }

    @GetMapping("/movies/search")
    public String searchMovies(
            @RequestParam(value = "query") String query, Model model) {
        List<MovieDto> movies = movieService.searchMovies(query);
        model.addAttribute("movies", movies);

        return "movies-list";
    }

    @GetMapping("/movies/new")
    public String createMovieForm(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);
        return "movies-create";
    }

    @PostMapping("/movies/new")
    public String saveMovie(@Valid @ModelAttribute("movie") MovieDto movieDto,
                            BindingResult result,
                            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("movie", movieDto);
            return "movies-create";
        }

        movieService.saveMovie(movieDto);
        return "redirect:/movies";
    }

    @GetMapping("/movies/{movieId}/edit")
    public String editMovieForm(@PathVariable("movieId") Long movieId,
                                Model model) {
        MovieDto movie = movieService.findMovieById(movieId);
        model.addAttribute("movie", movie);

        return "movies-edit";
    }

    @PostMapping("/movies/{movieId}/edit")
    public String editMovie(@PathVariable("movieId") Long movieId,
                            @Valid @ModelAttribute("movie") MovieDto movie,
                            BindingResult result) {

        if (result.hasErrors()) {
            return "movies-edit";
        }

        movie.setId(movieId);
        movieService.updateMovie(movie);

        return "redirect:/movies";
    }

    @GetMapping("/movies/{movieId}")
    public String movieDetails(@PathVariable("movieId") Long movieId, Model model) {
        var movieDto = movieService.findMovieById(movieId);

        model.addAttribute("movie", movieDto);
        return "movies-detail";
    }

    @GetMapping("/movies/{movieId}/delete")
    public String deleteMovie(@PathVariable("movieId") Long movieId) {
        movieService.deleteMovie(movieId);

        return "redirect:/movies";
    }
}
