package com.example.AdminDashboard.Controller;

import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.Service.RezervariService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class RezervariActive {

    @Autowired
    private RezervariService rezervariService;

    @GetMapping("/rezervari-active")
    public String findRezervariActive(Model model) {
        // Obținem datele din serviciu
        List<RezervareResponseDTO> rezervari = rezervariService.findRezervariActive();

        // Le adăugăm în model pentru Thymeleaf
        model.addAttribute("rezervari", rezervari);

        // Returnăm pagina HTML
        return "rezervari-active";
    }
}