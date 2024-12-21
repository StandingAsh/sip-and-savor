package com.project.demo.members.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        try {
            Authentication auth =
                    SecurityContextHolder.getContext().getAuthentication();
            model.addAttribute(auth.isAuthenticated());

            log.info("Current User: {}", auth.getName());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "home";
    }
}
