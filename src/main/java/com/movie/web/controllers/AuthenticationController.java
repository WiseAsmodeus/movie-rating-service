package com.movie.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AuthenticationController {

    @GetMapping("/auth/login")
    public String createLoginForm() {
        return "pages/authentication/login";
    }

    @GetMapping("/auth/signup")
    public String createSignupForm() {
        return "pages/authentication/signup";
    }
}
