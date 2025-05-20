package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.Converter.OaspeteDTOSimpluConverter;
import com.example.AdminDashboard.Converter.RezervareResponseDTOConverter;
import com.example.AdminDashboard.DTO.*;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.Service.CamereService;
import com.example.AdminDashboard.Service.OaspetiService;
import com.example.AdminDashboard.Service.RezervariService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    private final OaspetiService oaspetiService;
    private final OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter;
    private RezervareResponseDTO rezervareResponseDTO;
    private final RezervariService rezervariService;
    private final RezervareResponseDTOConverter rezervareResponseDTOConverter;
    private final CamereService camereService;
    private List<CameraDTO> camereValabile;

    public UserController(OaspetiService oaspetiService, RezervariService rezervariService, RezervareResponseDTOConverter rezervareResponseDTOConverter,
                          CamereService camereService, OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter)
    {
        this.rezervariService = rezervariService;
        this.oaspetiService = oaspetiService;
        this.rezervareResponseDTOConverter = rezervareResponseDTOConverter;
        this.camereService = camereService;
        this.oaspeteDTOSimpluConverter = oaspeteDTOSimpluConverter;
    }

    @GetMapping("/index")
    public String adminIndex() {
        return "user-index";
    }


    @GetMapping("/rezervarileMele")
    public String rezervarileMele(Model model, Principal principal) {
        String email = principal.getName();
        Oaspete oaspete = oaspetiService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("USER-UL NU EXISTA IN BAZA DE DATE!"));

        List<Rezervare> rezervari = oaspete.getRezervari();
        List<RezervareResponseDTO> rezervareResponseDTOS = rezervareResponseDTOConverter
                .convertListRezervareToListRezervareResponseDTO(rezervari);

        model.addAttribute("rezervari", rezervareResponseDTOS);
        return "user-rezervari";
    }


    @GetMapping("/rezervarileMele/{id}")
    public String getUserRezervareDetails(@PathVariable Integer id, Model model) {
        try {
            RezervareResponseDTO rezervare = rezervariService.findById(id); // Obține datele rezervării
            model.addAttribute("rezervare", rezervare); // Adaugă obiectul "rezervare" în model
            return "user-detalii-rezervare"; // Redă pagina corectă (numele fișierului HTML fără extensie)
        } catch (Exception e) {
            model.addAttribute("error", "Rezervarea cu ID-ul " + id + " nu a fost găsită!");
            return "rezervari"; // Alte pagini sau fallback în caz de eroare
        }
    }

    @PostMapping("/rezervarileMele")
    public String findUserRezervareByCodRezervare(@RequestParam String codRezervare, Model model) {
        RezervareSimplaDTO rezervare = null;

        try {
            rezervare = rezervariService.findRezervareByCodRezervare(codRezervare);
            if (rezervare != null) {
                model.addAttribute("rezervari", List.of(rezervare));
                model.addAttribute("success", true);
            } else {
                model.addAttribute("rezervari", Collections.emptyList());
                model.addAttribute("error", "Nu a fost găsită nicio rezervare cu codul: " + codRezervare);
            }
        } catch (Exception e) {
            model.addAttribute("rezervari", Collections.emptyList());
            model.addAttribute("error", "A apărut o eroare în timpul căutării. Vă rugăm încercați din nou.");
        }

        return "user-rezervari";
    }


    @GetMapping("/camere")
    public String findAll(Model model) {
        List<CameraDTO> camere = camereService.findAll();
        model.addAttribute("camere", camere);
        model.addAttribute("detaliiRezervare", new DetaliiRezervare());
        return "user-camere";
    }

    @PostMapping("/camere")
    public String verificaDisponibilitate(
            @ModelAttribute("detaliiRezervare") DetaliiRezervare detaliiRezervare,
            Model model) {

        try {
            List<CameraDTO> camereDisponibile = camereService.findAvailableRoomsByDatesAndCapacity(
                    detaliiRezervare.getCheckIn(),
                    detaliiRezervare.getCheckOut(),
                    detaliiRezervare.getPersoane()
            );
            if (!camereDisponibile.isEmpty()) {
                model.addAttribute("camere", camereDisponibile);
            } else {
                model.addAttribute("noResults", true);
            }

        } catch (Exception e) {
            model.addAttribute("errorMessage", "A apărut o eroare la căutarea camerelor disponibile.");
        }

        model.addAttribute("detaliiRezervare", detaliiRezervare);
        return "user-camere";
    }

    @GetMapping("/camere/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        CameraDTO camera = camereService.findById(id);
        model.addAttribute("camera", camera);
        return "user-detalii-camera";
    }



    @PostMapping("/rezervarileMele/creeazaRezervare")
    public String createRezervare(@ModelAttribute RezervareCreateDTO rezervareCreateDTO,
                                  Principal principal,
                                  RedirectAttributes redirectAttributes) {
        try {
            String email = principal.getName();
            rezervariService.saveRezervareByEmail(rezervareCreateDTO,email);


            redirectAttributes.addFlashAttribute("successMessage", "Rezervarea a fost creată cu succes!");
            return "redirect:/user/rezervarileMele";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Eroare la crearea rezervării: " + e.getMessage());
            return "redirect:/user/formular-rezervare";
        }
    }


    @GetMapping("/formular-rezervare")
    public String showRezervareForm(Model model) {
        model.addAttribute("rezervareCreateDTO", new RezervareCreateDTO());
//        model.addAttribute("camere", camereService.findAll());// pentru lista de camere

        return "user-formular-rez";
    }


    @GetMapping("/user/contulMeu")
    public ResponseEntity<UserOaspeteResponseDTO> getContulMeu(Principal principal)
    {
        String email = principal.getName();
        Oaspete oaspete = oaspetiService.findByEmail(email).orElse(null);

        if(oaspete == null)
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        OaspeteDTOSimplu oaspeteDTOSimplu = oaspeteDTOSimpluConverter.convertOaspeteToOaspeteDTOSimplu(oaspete);
        UserOaspeteResponseDTO userOaspeteResponseDTO = new UserOaspeteResponseDTO();

        userOaspeteResponseDTO.setIdOaspete(oaspeteDTOSimplu.getIdOaspete());
        userOaspeteResponseDTO.setEmail(oaspeteDTOSimplu.getEmail());
        userOaspeteResponseDTO.setNume(oaspeteDTOSimplu.getNume());
        userOaspeteResponseDTO.setPrenume(oaspeteDTOSimplu.getPrenume());
        userOaspeteResponseDTO.setOras(oaspeteDTOSimplu.getOras());
        userOaspeteResponseDTO.setTara(oaspeteDTOSimplu.getTara());
        userOaspeteResponseDTO.setStrada(oaspeteDTOSimplu.getStrada());


        return ResponseEntity.ok(userOaspeteResponseDTO);
    }


    @GetMapping("/contulMeu")
    public String getContulMeu(Principal principal, Model model) {
        String email = principal.getName();
        Oaspete oaspete = oaspetiService.findByEmail(email).orElse(null);

        if (oaspete == null) {
            return "redirect:/error";
        }

        OaspeteDTOSimplu oaspeteDTOSimplu = oaspeteDTOSimpluConverter.convertOaspeteToOaspeteDTOSimplu(oaspete);
        model.addAttribute("oaspete", oaspeteDTOSimplu);

        return "user-oaspeti";
    }

    @PostMapping("/sterge/contulMeu")
    public String deleteContulMeu(Principal principal) {
        System.out.println("S-A INCERCAT STERGEREA");
        String email = principal.getName();
        Oaspete oaspete = oaspetiService.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Utilizator inexistent"));

        oaspetiService.deleteOaspeteById(oaspete.getIdOaspete());
        return "redirect:/login";
    }


    @GetMapping("/contulMeu/edit")
    public String showEditForm(Model model, Principal principal) {
        String email = principal.getName();
        Oaspete oaspete = oaspetiService.findByEmail(email).orElse(null);

        if (oaspete == null) {
            return "redirect:/error";
        }

        UserOaspeteResponseDTO userDTO = new UserOaspeteResponseDTO();
        userDTO.setNume(oaspete.getNume());
        userDTO.setPrenume(oaspete.getPrenume());
        userDTO.setOras(oaspete.getOras());
        userDTO.setTara(oaspete.getTara());
        userDTO.setStrada(oaspete.getStrada());
        userDTO.setEmail(oaspete.getEmail());
        userDTO.setTelefon(oaspete.getTelefon());
        model.addAttribute("oaspete", userDTO);
        return "user-edit-oaspete";
    }

    @PostMapping("/contulMeu/edit")
    public String updateContulMeu(@ModelAttribute("oaspete") UserOaspeteResponseDTO userDTO,
                                  Principal principal,
                                  Model model, RedirectAttributes redirectAttributes) {
        try {
            String email = principal.getName();
            Oaspete oaspete = oaspetiService.findByEmail(email).orElse(null);

            if (oaspete == null) {
                model.addAttribute("error", "Utilizator nu a fost găsit!");
                return "user-edit-oaspete";
            }

            OaspeteDTOSimplu oaspeteDTOSimplu = new OaspeteDTOSimplu();
            oaspeteDTOSimplu.setNume(userDTO.getNume());
            oaspeteDTOSimplu.setPrenume(userDTO.getPrenume());
            oaspeteDTOSimplu.setOras(userDTO.getOras());
            oaspeteDTOSimplu.setTara(userDTO.getTara());
            oaspeteDTOSimplu.setStrada(userDTO.getStrada());
            oaspeteDTOSimplu.setEmail(email); // Important: folosim email-ul original
            oaspeteDTOSimplu.setParola(oaspete.getParola());
            oaspeteDTOSimplu.setTelefon(userDTO.getTelefon());
            System.out.println("Date primite: " + userDTO); // pentru debugging

            oaspetiService.updateOaspete(oaspete.getIdOaspete(), oaspeteDTOSimplu);
            redirectAttributes.addFlashAttribute("successMessage", "Contul a fost actualizat cu succes!");
            return "redirect:/user/contulMeu";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "A apărut o eroare la actualizarea contului!");
            return "redirect:/user/contulMeu/edit";

        }
    }

    @GetMapping("/contact-support")
    public String adminContact(){
        return "user-contact-support";
    }
    @GetMapping("/infoBD")
    public String adminInfoBD()
    {
        return "user-infoBD";
    }

}
