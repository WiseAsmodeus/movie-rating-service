package com.movie.web.controllers;

import com.movie.web.dto.RegistrationDto;
import com.movie.web.models.UserEntity;
import com.movie.web.services.UserService;
import com.movie.web.services.WishlistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final WishlistService wishlistService;

    @GetMapping("/auth/login")
    public String createLoginForm() {

        return "pages/authentication/login";
    }


    @GetMapping("/auth/signup")
    public String createSignupForm(Model model) {

        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user", user);

        return "pages/authentication/signup";
    }

    @PostMapping("/auth/signup/save")
    public String signup(@Valid @ModelAttribute("user") RegistrationDto user,
                         BindingResult result,
                         Model model)
    {

        if (userService.userWithEmailExists(user.getEmail())) {
            return "redirect:/auth/signup?fail";
        }

        if (userService.userWithNameExists(user.getUsername())) {
            return "redirect:/auth/signup?fail";
        }

        if (!user.getPassword().equals(user.getRepeatPassword())) {
            result.rejectValue("repeatPassword", "error","Пароли не совпадают!");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "pages/authentication/signup";
        }

        var createdUser = userService.saveUser(user);
        wishlistService.createWishlistForUser(createdUser.getUsername());

        return "redirect:/movies?success";
    }
}
