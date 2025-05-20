package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.DTO.DetaliiRezervare;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Service.CamereService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping(path = "/admin/camere")
public class CamereController {

    private final CamereService camereService;

    public CamereController(CamereService camereService) {
        this.camereService = camereService;
    }

    @GetMapping
    public String findAll(Model model) {
        // Obține toate camerele utilizând repository-ul/service-ul
        List<CameraDTO> camere = camereService.findAll();
        model.addAttribute("camere", camere); // Adaugă lista în model

        // Adaugă un obiect gol pentru formularul Thymeleaf
        model.addAttribute("detaliiRezervare", new DetaliiRezervare());

        return "camere"; // Returnează pagina Thymeleaf
    }




    @PostMapping()
    public String verificaDisponibilitate(
            @ModelAttribute("detaliiRezervare") DetaliiRezervare detaliiRezervare,
            Model model) {

        try {
            // Obține camerele disponibile
            List<CameraDTO> camereDisponibile = camereService.findAvailableRoomsByDatesAndCapacity(
                    detaliiRezervare.getCheckIn(),
                    detaliiRezervare.getCheckOut(),
                    detaliiRezervare.getPersoane()
            );
            if (!camereDisponibile.isEmpty()) {
                model.addAttribute("camere", camereDisponibile); // Camere disponibile
            } else {
                model.addAttribute("noResults", true); // Adaugă atribut pentru lipsa camerelor
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", "A apărut o eroare la căutarea camerelor disponibile.");
        }

        // Reatașează obiectul pentru formular
        model.addAttribute("detaliiRezervare", detaliiRezervare);

        // Returnează pagina
        return "camere";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        CameraDTO camera = camereService.findById(id);
        model.addAttribute("camera", camera);
        return "detalii-camera";
    }

    @PostMapping("/creeazaCamera")
    public String createCamera(@RequestBody CameraDTO cameraDTO)
    {
        return camereService.saveCamera(cameraDTO);
    }

    @PutMapping("/{id}/updateCamera")
    public CameraDTO updateCamera(@PathVariable Integer id,@RequestBody CameraDTO cameraDTO)
    {
        return camereService.updateCamera(id,cameraDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteCamera(@PathVariable Integer id)
    {
        return camereService.deleteById(id);
    }
}
