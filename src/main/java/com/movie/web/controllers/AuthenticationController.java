package com.movie.web.controllers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class AuthenticationController {

    @GetMapping("/login")
    public String createLoginForm() {
        return "pages/authentication/login";
    }

}
