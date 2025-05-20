package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Entity.Token;
import com.example.AdminDashboard.Event.RegistrationCompleteEvent;
import com.example.AdminDashboard.Repository.TokenRepository;
import com.example.AdminDashboard.Service.EmailService;
import com.example.AdminDashboard.Service.OaspetiService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/register")
public class RegisterController {
    private final OaspetiService oaspetiService;
    private final ApplicationEventPublisher publisher;
    private final TokenRepository tokenRepository;

    public RegisterController(OaspetiService oaspetiService, ApplicationEventPublisher publisher, TokenRepository tokenRepository)
    {
        this.oaspetiService = oaspetiService;
        this.publisher = publisher;
        this.tokenRepository = tokenRepository;
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("oaspete", new OaspeteDTOSimplu());
        return "formular-oaspete";  // numele template-ului
    }



    @PostMapping
    public String saveRegisteredOaspete(@ModelAttribute("oaspete") OaspeteDTOSimplu oaspeteDTOSimplu,
                                        HttpServletRequest httpServletRequest,
                                        Model model) {
        try {
            // Salvăm oaspetele
            Oaspete savedOaspete = oaspetiService.save(oaspeteDTOSimplu);

            // Publicăm evenimentul de înregistrare care va genera token-ul și va trimite email-ul
            publisher.publishEvent(
                    new RegistrationCompleteEvent(applcationUrl(httpServletRequest),oaspeteDTOSimplu)
            );

            model.addAttribute("successMessage",
                    "Înregistrat cu succes! Vă rugăm să verificați email-ul pentru a activa contul.");
            return "formular-oaspete"; // rămânem pe aceeași pagină pentru a arăta mesajul

        } catch (Exception e) {
            model.addAttribute("errorMessage",
                    "A apărut o eroare la înregistrare: " + e.getMessage());
            return "formular-oaspete";
        }
    }

    public String applcationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
    }

    @GetMapping("/verificareEmail")
    public String verificareEmail(@RequestParam("token") String token) {
        Token verificareToken = tokenRepository.findByToken(token);

        if(verificareToken.getOaspete().getEsteActivat()) {
            return "already-verified";
        }

        String rezultatVerificare = oaspetiService.validareToken(token);
        if(rezultatVerificare.equals("valid")) {
            return "verification-succes";
        }

        oaspetiService.deleteOaspeteById(verificareToken.getOaspete().getIdOaspete());
        return "verification-failed";
    }
}
