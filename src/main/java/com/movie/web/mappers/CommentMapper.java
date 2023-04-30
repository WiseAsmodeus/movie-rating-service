package com.movie.web.mappers;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.movie.web.dto.CommentDto;
import com.movie.web.models.Comment;

import java.time.ZoneOffset;

public class CommentMapper {
    public static Comment mapToComment(CommentDto commentDto) {
        return Comment.builder()
                .id(commentDto.getId())
                .text(commentDto.getText())
                .build();
    }

    public static CommentDto mapToCommentDto(Comment comment) {

        var createdTime = TimeAgo.using(comment.getCreatedOn().toInstant(
             ZoneOffset.UTC).toEpochMilli());

        return CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .username(comment.getPostedBy().getUsername())
                .createdOn(createdTime)
                .userAvatarUrl(comment.getPostedBy().getAvatarImageUrl())
                .build();
    }
}
