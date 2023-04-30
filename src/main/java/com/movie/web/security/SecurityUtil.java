package com.movie.web.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtil {
    public static String getSessionUser() {

        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();



        return authentication.getName();
    }
}
