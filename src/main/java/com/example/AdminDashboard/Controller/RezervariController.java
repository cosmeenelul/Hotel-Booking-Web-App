package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.Repository.RezervariRepository;
import com.example.AdminDashboard.Service.RezervariService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/rezervari")
public class RezervariController {

    private final RezervariService rezervariService;

    public RezervariController(RezervariService rezervariService)
    {
        this.rezervariService = rezervariService;
    }


    @GetMapping("/toateRezervarile")
    public List<Rezervare> findAll()
    {
        return rezervariService.findAll();
    }
}
