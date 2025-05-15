package com.example.AdminDashboard.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping("/")
    public String redirectLogin()
    {
        return "redirect:/login";
    }
}
