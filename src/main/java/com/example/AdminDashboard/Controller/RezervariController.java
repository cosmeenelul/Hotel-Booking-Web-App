package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.RezervareCreateDTO;
import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.DTO.RezervareSimplaDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.DTO.DetaliiRezervare;
import com.example.AdminDashboard.Service.RezervariService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/rezervari")
public class RezervariController {

    private final RezervariService rezervariService;

    public RezervariController(RezervariService rezervariService)
    {
        this.rezervariService = rezervariService;
    }


    @GetMapping()
    public String getAllRezervari(Model model) {
        List<RezervareSimplaDTO> rezervari = rezervariService.findAll();
        model.addAttribute("rezervari", rezervari);
        return "rezervari"; // Numele fișierului HTML Thymeleaf (rezervari.html)
    }


    @GetMapping("/{id}")
    public String showRezervareDetails(@PathVariable Integer id, Model model) {
        try {
            RezervareResponseDTO rezervare = rezervariService.findById(id);
            model.addAttribute("rezervare", rezervare);
            return "detalii-rezervare";
        } catch (Exception e) {
            model.addAttribute("error", "Rezervarea cu ID-ul " + id + " nu a fost găsită!");
            return "rezervari"; // Redirecționează în cazul unei erori
        }
    }



    @PostMapping("/addRezervare")
    public RezervareCreateDTO createRezervare(@RequestBody RezervareCreateDTO rezervareCreateDTO)
    {
        return rezervariService.saveRezervare(rezervareCreateDTO);
    }

    @PostMapping("/cauta")
    public String findRezervareByCodRezervare(@RequestParam String codRezervare, Model model) {
        RezervareSimplaDTO rezervare = null;
        try {
            // Încearcă să găsești rezervarea
            rezervare = rezervariService.findRezervareByCodRezervare(codRezervare);

        } catch (Exception e) {
            // Tratăm situațiile în care căutarea eșuează
            model.addAttribute("notFound", true);
        }

        // Dacă rezervarea este găsită, o adăugăm la model
        if (rezervare != null) {
            model.addAttribute("rezervari", List.of(rezervare)); // Trimitem rezervarea sub formă de listă
        }

        // Reîntoarcem pagina Thymeleaf
        return "rezervari";
    }




    @PutMapping("/{id}/updateRezervare")
    public void updateRezervare(@PathVariable Integer id, @RequestBody RezervareCreateDTO rezervareCreateDTO)
    {
        rezervariService.updateRezervare(id,rezervareCreateDTO);
    }


    @DeleteMapping("/{id}/deleteRezervare")
    public String deleteRezervareById(@PathVariable Integer id)
    {
        rezervariService.deleteById(id);
        return "redirect:/stergere-succes.html";
    }


}

