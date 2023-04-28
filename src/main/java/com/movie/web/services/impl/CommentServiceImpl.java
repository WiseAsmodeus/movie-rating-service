package com.movie.web.services.impl;

import com.movie.web.dto.CommentDto;
import com.movie.web.models.Movie;
import com.movie.web.models.Comment;
import com.movie.web.repositories.MovieRepository;
import com.movie.web.repositories.CommentRepository;
import com.movie.web.services.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;

    @Override
    public void createComment(Long movieId, CommentDto commentDto) {
        Movie movie = movieRepository.findById(movieId).get();

        Comment review = mapToComment(commentDto);
        review.setMovie(movie);

        commentRepository.save(review);
    }

    @Override
    public List<CommentDto> getMovieComments(Long movieId) {

        Movie movie = movieRepository.findById(movieId).get();
        var movieComments = commentRepository.findAllByMovie(movie);

        return movieComments.stream()
                .map(this::mapToCommentDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public Comment mapToComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .createdOn(commentDto.getCreatedOn())
                .updatedOn(commentDto.getUpdatedOn())
                .text(commentDto.getText())
                .build();
    }

    public CommentDto mapToCommentDto(Comment comment) {
        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .username("Custom username")
                .createdOn(comment.getCreatedOn())
                .updatedOn(comment.getUpdatedOn())
                .build();
    }
}
