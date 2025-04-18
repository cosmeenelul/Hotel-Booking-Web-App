package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Service.OaspetiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/oaspeti")
public class OaspetiController {
    private final OaspetiService oaspetiService;


    public OaspetiController(OaspetiService oaspetiService)
    {
        this.oaspetiService = oaspetiService;
    }

    @GetMapping("/totiOaspetii")
    public List<OaspeteDTO> findAll()
    {
        return oaspetiService.findAll();
    }

    @PostMapping("/addOaspete")
    public ResponseEntity<?> addOaspete(@RequestBody OaspeteDTO oaspeteDTO)
    {
        if(oaspetiService.existsByTelefon(oaspeteDTO.getTelefon()))
        {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Persoană deja existentă");
        }
        Oaspete oaspeteSalvat = oaspetiService.save(oaspeteDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(oaspeteSalvat);
    }
}
