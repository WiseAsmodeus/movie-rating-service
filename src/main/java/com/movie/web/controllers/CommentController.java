package com.movie.web.controllers;

import com.movie.web.dto.CommentDto;
import com.movie.web.services.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{movieId}/create")
    public String postMovieComment(@PathVariable("movieId") Long movieId,
                                   @ModelAttribute("comment") CommentDto comment)
    {
        commentService.createComment(movieId, comment);

        return "redirect:/movies/" + movieId;
    }

    @GetMapping("/comments/{movieId}/delete/{commentId}")
    public String deleteComment(
            @PathVariable("movieId") Long movieId,
            @PathVariable("commentId") Long commentId)
    {
        commentService.deleteCommentById(commentId);

        return "redirect:/movies/" + movieId;
    }
}
