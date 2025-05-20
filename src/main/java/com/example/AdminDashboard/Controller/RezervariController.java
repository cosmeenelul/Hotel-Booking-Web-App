package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.RezervareCreateDTO;
import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.DTO.RezervareSimplaDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.DTO.DetaliiRezervare;
import com.example.AdminDashboard.Service.CamereService;
import com.example.AdminDashboard.Service.RezervariService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path = "/admin/rezervari")
public class RezervariController {
    @Autowired
    private CamereService camereService;


    private final RezervariService rezervariService;

    public RezervariController(RezervariService rezervariService)
    {
        this.rezervariService = rezervariService;
    }


    @GetMapping()
    public String getAllRezervari(Model model) {
        List<RezervareSimplaDTO> rezervari = rezervariService.findAll();
        model.addAttribute("rezervari", rezervari);
        return "rezervari";
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

    @DeleteMapping("/{id}/deleteRezervare")
    public String deleteRezervare(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            rezervariService.deleteById(id);
            redirectAttributes.addFlashAttribute("successMessage", "Rezervarea a fost ștearsă cu succes.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A apărut o eroare la ștergerea rezervării.");
        }
        return "redirect:/admin/rezervari"; // Redirecționează către lista de rezervări
    }



    @GetMapping("/formular-rezervare")
    public String showRezervareForm(Model model) {
        model.addAttribute("rezervareCreateDTO", new RezervareCreateDTO());
        model.addAttribute("camere", camereService.findAll());

        return "formular-rez";
    }


    @PostMapping("/creeazaRezervare")
    public String createRezervare(@ModelAttribute RezervareCreateDTO rezervareCreateDTO, Model model) {
        try {
            RezervareCreateDTO savedRezervare = rezervariService.saveRezervare(rezervareCreateDTO);
            // Redirectionare către pagina de rezervări după salvare reușită
            return "redirect:/admin/rezervari";
        } catch (Exception e) {
            // În caz de eroare, adăugăm mesajul de eroare în model
            model.addAttribute("error", "Nu s-a putut crea rezervarea: " + e.getMessage());
            model.addAttribute("rezervareCreateDTO", rezervareCreateDTO);
            model.addAttribute("camere", camereService.findAll());
            // Întoarcem utilizatorul la formular cu mesajul de eroare
            return "formular-rez";
        }
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




    @GetMapping("/{id}/edit")
    public String showUpdateForm(@PathVariable Integer id, Model model) {
        // Presupun că ai o metodă care returneaza rezervarea după ID
        RezervareResponseDTO rezervare = rezervariService.findById(id);
        model.addAttribute("rezervare", rezervare);
        return "edit-rezervare"; // numele template-ului HTML
    }

    @PostMapping("/{id}/update")
    public String updateRezervare(@PathVariable Integer id,
                                  @ModelAttribute RezervareCreateDTO rezervareCreateDTO,
                                  RedirectAttributes redirectAttributes) {
        rezervariService.updateRezervare(id, rezervareCreateDTO);
        redirectAttributes.addFlashAttribute("message", "Rezervarea a fost actualizată cu succes!");
        return "redirect:/admin/rezervari"; // redirect către lista de rezervări
    }


}

