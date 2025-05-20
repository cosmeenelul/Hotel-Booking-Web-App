package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Service.OaspetiService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/oaspeti")
public class OaspetiController {
    private final OaspetiService oaspetiService;


    public OaspetiController(OaspetiService oaspetiService)
    {
        this.oaspetiService = oaspetiService;
    }

    // GET REQUESTS
    @GetMapping()
    public String findAll(Model model) {
        List<OaspeteDTOSimplu> oaspeti = oaspetiService.findAll();
        model.addAttribute("oaspeti", oaspeti);
        return "oaspeti"; // numele template-ului HTML (oaspeti.html)
    }



    @GetMapping("/{id}")
    public OaspeteDTO findOaspeteById(@PathVariable Integer id)
    {
        return oaspetiService.findById(id);
    }
    @GetMapping("/search")
    public String findOaspeteByTelefon(@RequestParam String telefon, Model model) {
        try {
            OaspeteDTO oaspete = oaspetiService.findByTelefon(telefon);
            model.addAttribute("oaspeti", List.of(oaspete));
        } catch (Exception e) {
            model.addAttribute("oaspeti", new ArrayList<>()); // listă goală
            model.addAttribute("mesajEroare", "Nu s-a găsit niciun oaspete cu acest număr de telefon");
        }
        return "oaspeti";
    }


    //POST
    @PostMapping("/addOaspete")
    public String addOaspete(@ModelAttribute OaspeteDTOSimplu oaspeteDTOSimplu) {
        oaspetiService.save(oaspeteDTOSimplu);
        return "redirect:/admin/oaspeti";
    }


    //DELETE
    @DeleteMapping("/{id}")
    public String deleteById(@PathVariable Integer id) {
        oaspetiService.deleteOaspeteById(id);
        return "redirect:/admin/oaspeti";
    }



    //PUT
    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model) {
        try {
            OaspeteDTOSimplu oaspete = oaspetiService.findDTOSimpluById(id);
            if (oaspete == null) {
                return "redirect:/admin/oaspeti";
            }

            model.addAttribute("oaspete", oaspete); // adăugăm obiectul în model
            model.addAttribute("id", id);
            return "edit-oaspete";
        } catch (Exception e) {
            return "redirect:/admin/oaspeti";
        }
    }

    @PutMapping("/{id}/updateOaspete")
    public String updateOaspete(@PathVariable Integer id,
                                @ModelAttribute("oaspete") OaspeteDTOSimplu oaspeteDTOSimplu,
                                Model model) {
        try {
            oaspetiService.updateOaspete(id, oaspeteDTOSimplu);
            return "redirect:/admin/oaspeti";
        } catch (Exception e) {
            model.addAttribute("error", "Eroare la actualizare: " + e.getMessage());
            model.addAttribute("oaspete", oaspeteDTOSimplu);
            model.addAttribute("id", id);
            return "edit-oaspete";
        }
    }

}
