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
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final OaspetiService oaspetiService;
    private final OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter;
    private RezervareResponseDTO rezervareResponseDTO;
    private final RezervariService rezervariService;
    private final RezervareResponseDTOConverter rezervareResponseDTOConverter;
    private final CamereService camereService;

    public UserController(OaspetiService oaspetiService, RezervariService rezervariService, RezervareResponseDTOConverter rezervareResponseDTOConverter,
                          CamereService camereService, OaspeteDTOSimpluConverter oaspeteDTOSimpluConverter)
    {
        this.rezervariService = rezervariService;
        this.oaspetiService = oaspetiService;
        this.rezervareResponseDTOConverter = rezervareResponseDTOConverter;
        this.camereService = camereService;
        this.oaspeteDTOSimpluConverter = oaspeteDTOSimpluConverter;
    }



    @GetMapping("/rezervarileMele")
    public ResponseEntity<List<RezervareResponseDTO>> rezervarileMele(Principal principal)
    {
        String email = principal.getName();
        Oaspete oaspete = oaspetiService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("USER-UL NU EXISTA IN BAZA DE DATE!"));
        List<Rezervare> rezervari = oaspete.getRezervari();


        List<RezervareResponseDTO> rezervareResponseDTOS = rezervareResponseDTOConverter
                .convertListRezervareToListRezervareResponseDTO(rezervari);

        if(rezervareResponseDTOS.isEmpty())
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(rezervareResponseDTOS);
    }
    @PostMapping("/verificaDisponibilitate")
    public List<CameraDTO> verificaDisponibilitate(@RequestBody DetaliiRezervare detaliiRezervare)
    {
        return camereService.findAvailableRoomsByDatesAndCapacity(detaliiRezervare.getCheckIn(),detaliiRezervare.getCheckOut(),
                detaliiRezervare.getPersoane());
    }
    @GetMapping("/camere")
    public List<CameraDTO> findCamere()
    {
        return camereService.findAll();
    }
    @GetMapping("/camera/{id}")
    public CameraDTO findById(@PathVariable Integer id)
    {
        return camereService.findById(id);
    }

    @PostMapping("/rezervarileMele/creeazaRezervare")
    public RezervareCreateDTO createRezervare(@RequestBody RezervareCreateDTO rezervareCreateDTO)
    {
        return rezervariService.saveRezervare(rezervareCreateDTO);
    }

    @GetMapping("/contulMeu")
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

    @DeleteMapping("/contulMeu")
    public String deleteContulMeu(Principal principal)
    {
        String email = principal.getName();
        Oaspete oaspete = oaspetiService.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Utilizator inexistent"));

        oaspetiService.deleteOaspeteById(oaspete.getIdOaspete());
        return "Utilizator sters cu succes!";
    }

    @PutMapping("/contulMeu")
    public ResponseEntity<String> updateContulMeu(@RequestBody UserOaspeteResponseDTO userOaspeteResponseDTO, Principal principal) {
        String email = principal.getName();

        Oaspete oaspete = oaspetiService.findByEmail(email).orElse(null);

        // Dacă utilizatorul nu există, returnăm 404 NOT FOUND
        if (oaspete == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizator nu a fost găsit!");
        }
        OaspeteDTOSimplu oaspeteDTOSimplu = new OaspeteDTOSimplu();
        // Actualizăm câmpurile din entitatea Oaspete cu noile date primite
        oaspeteDTOSimplu.setNume(userOaspeteResponseDTO.getNume());
        oaspeteDTOSimplu.setPrenume(userOaspeteResponseDTO.getPrenume());
        oaspeteDTOSimplu.setOras(userOaspeteResponseDTO.getOras());
        oaspeteDTOSimplu.setTara(userOaspeteResponseDTO.getTara());
        oaspeteDTOSimplu.setStrada(userOaspeteResponseDTO.getStrada());

        // Salvăm modificările în baza de date
        oaspetiService.updateOaspete(oaspete.getIdOaspete(),oaspeteDTOSimplu); // Aceasta ar fi o metodă ce efectuează salvarea

        // Returnăm un răspuns de succes
        return ResponseEntity.ok("Contul a fost actualizat cu succes!");
    }
}
