package com.example.AdminDashboard.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin/index")
    public String adminIndex() {
        return "admin-index";
    }
    @GetMapping("/admin/contact-support")
    public String adminContact(){
        return "contact-support";
    }
    @GetMapping("/admin/infoBD")
    public String adminInfoBD()
    {
        return "info-bd";
    }
}
