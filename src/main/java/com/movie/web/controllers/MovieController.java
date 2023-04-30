package com.movie.web.controllers;

import com.movie.web.dto.CommentDto;
import com.movie.web.dto.GenreDto;
import com.movie.web.dto.MovieDto;
import com.movie.web.models.Genre;
import com.movie.web.models.Movie;
import com.movie.web.security.SecurityUtil;
import com.movie.web.services.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@RequestMapping
public class MovieController {

    private final MovieService movieService;
    private final CommentService commentService;
    private final GenreService genreService;
    private final UserService userService;
    private final WishlistService wishlistService;

    @GetMapping("/movies")
    public String listMovies(Model model) {

        var movies = movieService.getAll();
        model.addAttribute("movies", movies);

        return "pages/movies/movies-list";
    }

    @GetMapping("/movies/search")
    public String searchMovies(
            @RequestParam(value = "query") String query, Model model) {
        List<MovieDto> movies = movieService.searchMovies(query);
        model.addAttribute("movies", movies);

        return "pages/movies/movies-list";
    }

    @GetMapping("/movies/genre--{genreName}")
    public String createMoviesWithinGenre(@PathVariable("genreName") String genreName,
                                          Model model) {

        model.addAttribute("genreName", genreName);

        var movies = movieService.findMoviesByGenreName(genreName);
        model.addAttribute("movies", movies);

        for (var movie : movies) {
            System.out.println(movie.toString());
        }

        return "/pages/movies/movies-by-genre";
    }

    @GetMapping("/movies/new")
    public String createMovieForm(Model model) {
        Movie movie = new Movie();
        model.addAttribute("movie", movie);

        List<String> genres = genreService.getAllGenres()
                .stream().map(GenreDto::getName)
                .collect(Collectors.toList());
        model.addAttribute("genres", genres);

        return "pages/movies/movies-create";
    }

    @PostMapping("/movies/new")
    public String saveMovie(@Valid @ModelAttribute("movie") MovieDto movieDto,
                            @RequestParam("genres") List<String> genres,
                            BindingResult result,
                            Model model) {

        if (result.hasErrors()) {
            model.addAttribute("movie", movieDto);
            return "pages/movies/movies-create";
        }

        movieDto.setGenres(genres);
        movieService.saveMovie(movieDto);
        return "redirect:/movies";
    }

    @GetMapping("/movies/{movieId}/edit")
    public String editMovieForm(@PathVariable("movieId") Long movieId,
                                Model model) {

        MovieDto movie = movieService.findMovieById(movieId);
        model.addAttribute("movie", movie);

        List<String> genres = genreService.getAllGenres()
                .stream().map(GenreDto::getName)
                .collect(Collectors.toList());
        model.addAttribute("genres", genres);

        return "pages/movies/movies-edit";
    }

    @PostMapping("/movies/{movieId}/edit")
    public String editMovie(@PathVariable("movieId") Long movieId,
                            @Valid @ModelAttribute("movie") MovieDto movie,
                            @RequestParam("genres") List<String> genres,
                            BindingResult result) {

        if (result.hasErrors()) {
            return "pages/movies/movies-edit";
        }

        movie.setId(movieId);
        movie.setGenres(genres);

        movieService.updateMovie(movie);

        return "redirect:/movies";
    }

    @GetMapping("/movies/{movieId}")
    public String movieDetails(@PathVariable("movieId") Long movieId, Model model) {
        var movieDto = movieService.findMovieById(movieId);
        model.addAttribute("movie", movieDto);

        CommentDto comment = new CommentDto();
        model.addAttribute("commentForm", comment);

        List<GenreDto> movieGenres = genreService.findGenresByMovieId(movieId);
        model.addAttribute("movieGenres", movieGenres);

        var movieComments = commentService.getMovieComments(movieId);
        model.addAttribute("movieComments", movieComments);


        var user = userService.getByUsername(SecurityUtil.getSessionUser());
        if (user != null) {
            model.addAttribute("nullWishlist", false);
            model.addAttribute("user", user);
            model.addAttribute("existsInWishlist",
                    wishlistService.filmExistsInUserWishlist(movieId, user.getUsername()));
        }
        else {
            model.addAttribute("nullWishlist", true);
        }

        return "pages/movies/movies-detail";
    }

    @GetMapping("/movies/{movieId}/delete")
    public String deleteMovie(@PathVariable("movieId") Long movieId) {

        commentService.deleteMovieComments(movieId);
        movieService.deleteMovie(movieId);

        return "redirect:/movies";
    }
}
