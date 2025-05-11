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
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/register")
public class RegisterController {
    private final OaspetiService oaspetiService;
    private final ApplicationEventPublisher publisher;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    public RegisterController(OaspetiService oaspetiService, ApplicationEventPublisher publisher, TokenRepository tokenRepository, EmailService emailService)
    {
        this.oaspetiService = oaspetiService;
        this.publisher = publisher;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @PostMapping
    public String saveRegisteredOaspete(@RequestBody OaspeteDTOSimplu oaspeteDTOSimplu, final HttpServletRequest httpServletRequest)
    {
        Oaspete savedOaspete = oaspetiService.save(oaspeteDTOSimplu);
        publisher.publishEvent(new RegistrationCompleteEvent(applcationUrl(httpServletRequest),oaspeteDTOSimplu));


        return "Inregistrat cu succes! Verificati-va email-ul pentru a putea continua";
    }

    public String applcationUrl(HttpServletRequest httpServletRequest) {
        return "http://"+httpServletRequest.getServerName()+":"+httpServletRequest.getServerPort()+httpServletRequest.getContextPath();
    }

    @GetMapping("/verificareEmail")
    public String verificareEmail(@RequestParam("token") String token)
    {
        Token verificareToken = tokenRepository.findByToken(token);

        if(verificareToken.getOaspete().getEsteActivat())
        {
            return "Acest cont a fost deja validat, va rog sa va logati!";
        }
        String rezultatVerificare = oaspetiService.validareToken(token);
        if(rezultatVerificare.equals("valid"))
        {
            return "Verificarea a fost realizata cu succes !";
        }
        oaspetiService.deleteOaspeteById(verificareToken.getOaspete().getIdOaspete());
        return "Verificare invalida! Inregistrati-va din nou";
    }
}
