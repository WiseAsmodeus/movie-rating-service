package com.movie.web.mappers;

import com.movie.web.dto.UserDto;
import com.movie.web.models.UserEntity;

public class UserMapper {
    public static UserDto mapToUserDto(UserEntity user) {
        return UserDto.builder()
                .id(user.getId())
                .avatarImageUrl(user.getAvatarImageUrl())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }
}
