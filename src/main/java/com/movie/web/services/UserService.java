package com.movie.web.services;

import com.movie.web.dto.RegistrationDto;
import com.movie.web.models.UserEntity;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    boolean userAlreadyExists(String email, String username);
    boolean userWithEmailExists(String email);
    boolean userWithNameExists(String username);
    UserEntity getByUsername(String sessionUser);
}
