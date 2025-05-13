package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Service.OaspetiService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    // GET REQUESTS
    @GetMapping("/totiOaspetii")
    public List<OaspeteDTOSimplu> findAll()
    {
        return oaspetiService.findAll();
    }
    @GetMapping("/totiOaspetii/{id}")
    public OaspeteDTO findOaspeteById(@PathVariable Integer id)
    {
        return oaspetiService.findById(id);
    }
    @GetMapping("/totiOaspetii/findByTelefon/{telefon}")
    public OaspeteDTO findOaspeteByTelefon(@PathVariable String telefon)
    {
        return oaspetiService.findByTelefon(telefon);
    }


    //POST
    @PostMapping("/addOaspete")
    public Oaspete addOaspete(@RequestBody OaspeteDTOSimplu oaspeteDTOSimplu)
    {
        return oaspetiService.save(oaspeteDTOSimplu);
    }

    //DELETE
    @DeleteMapping("/{id}/deleteOaspete")
    public String deleteById(@PathVariable Integer id)
    {
        return oaspetiService.deleteOaspeteById(id);
    }


    //PUT
    @PutMapping("/{id}/updateOaspete")
    public String updateOaspete(@PathVariable Integer id,@RequestBody OaspeteDTOSimplu oaspeteDTOSimplu)
    {
        return oaspetiService.updateOaspete(id,oaspeteDTOSimplu);
    }
}
