package com.yourorg.licenserenewalmanager.license.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        // send root to your dashboard UI
        return "redirect:/ui/dashboard";
    }

    @GetMapping("/ui/test")
    public String test() {
        return "test"; // resolves to /WEB-INF/jsp/test.jsp
    }
}