package com.project.demo.domain.members.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class SignUpController {

    @GetMapping("/")
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER"));
        model.addAttribute("isLoggedIn", isLoggedIn);
        log.info("Current Authorities: {}", auth.getAuthorities());
        return "home";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute(auth.isAuthenticated());

            log.info("Current User: {}", auth.getName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "members/signUp";
    }
}
