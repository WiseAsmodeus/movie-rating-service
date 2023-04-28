package com.movie.web.services;

import com.movie.web.dto.CommentDto;

import java.util.List;

public interface CommentService {
    void createComment(Long movieId, CommentDto reviewDto);
    List<CommentDto> getMovieComments(Long movieId);

    void deleteCommentById(Long commentId);
}
