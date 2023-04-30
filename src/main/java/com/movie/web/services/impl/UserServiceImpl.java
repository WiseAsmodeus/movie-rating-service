package com.movie.web.services.impl;

import com.movie.web.dto.RegistrationDto;
import com.movie.web.models.Role;
import com.movie.web.models.UserEntity;
import com.movie.web.repositories.RoleRepository;
import com.movie.web.repositories.UserRepository;
import com.movie.web.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(RegistrationDto registrationDto) {
        UserEntity user = UserEntity.builder()
                .username(registrationDto.getUsername())
                .email(registrationDto.getEmail())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .avatarImageUrl(registrationDto.getAvatarImageUrl())
                .build();

        Role role = roleRepository.findByName("USER");
        user.setRoles(Arrays.asList(role));

        userRepository.save(user);
    }

    @Override
    public boolean userAlreadyExists(String email, String username) {
        return userRepository.findByUsernameOrEmail(username, email) != null;
    }

    @Override
    public boolean userWithEmailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    @Override
    public boolean userWithNameExists(String username) {
        return userRepository.findByUsername(username) != null;
    }
}
