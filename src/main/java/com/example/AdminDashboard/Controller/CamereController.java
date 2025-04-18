package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Service.CamereService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/camere")
public class CamereController {

    private final CamereService camereService;

    public CamereController(CamereService camereService) {
        this.camereService = camereService;
    }

    @GetMapping("/toateCamerele")
    public List<CameraDTO> findAll()
    {
        return camereService.findAll();
    }
}
