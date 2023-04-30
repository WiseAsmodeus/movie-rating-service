package com.movie.web.controllers;

import com.movie.web.mappers.UserMapper;
import com.movie.web.security.SecurityUtil;
import com.movie.web.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@AllArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping("/profile")
    public String getUserProfile() {

        var currentUsername = SecurityUtil.getSessionUser();

        return "redirect:/profile/" + currentUsername;
    }

    @GetMapping("/profile/{username}")
    public String createUserProfilePage(
            Model model,
            @PathVariable("username") String username
    ) {

        var user = UserMapper.mapToUserDto(userService.getByUsername(username));
        model.addAttribute("user", user);

        return "pages/user/profile";
    }
}
