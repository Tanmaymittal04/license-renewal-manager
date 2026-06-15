package com.yourorg.licenserenewalmanager.license.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // Spring Security will intercept unauthenticated users
        // and redirect them to /ui/login automatically.
        // Authenticated users hitting "/" go straight to dashboard.
        return "redirect:/ui/dashboard";
    }

    @GetMapping("/ui/login")
    public String loginPage(
            @RequestParam(name = "error", required = false) String error,
            @RequestParam(name = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("errorMsg", "Invalid email or password.");
        }
        if (logout != null) {
            model.addAttribute("logoutMsg", "You have been logged out.");
        }
        return "auth/login";  // /WEB-INF/jsp/auth/login.jsp
    }

    @GetMapping("/ui/test")
    public String test() {
        return "test";
    }
}