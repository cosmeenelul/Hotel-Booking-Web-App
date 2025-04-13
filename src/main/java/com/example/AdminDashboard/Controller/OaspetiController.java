package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Service.OaspetiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/oaspeti")
public class OaspetiController {
    private final OaspetiService oaspetiService;

    public OaspetiController(OaspetiService oaspetiService)
    {
        this.oaspetiService = oaspetiService;
    }

    @GetMapping("/totiOaspetii")
    public List<Oaspete> findAll()
    {
        return oaspetiService.findAll();
    }

}
