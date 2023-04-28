package com.movie.web.repositories;

import com.movie.web.models.Comment;
import com.movie.web.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByMovie(Movie movie);
}
