package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.OaspeteDTO;
import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Service.OaspetiService;
import jakarta.persistence.criteria.CriteriaBuilder;
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
    public List<OaspeteDTOSimplu> findAll()
    {
        return oaspetiService.findAll();
    }

    @PostMapping("/addOaspete")
    public Oaspete addOaspete(@RequestBody OaspeteDTOSimplu oaspeteDTOSimplu)
    {
        return oaspetiService.save(oaspeteDTOSimplu);
    }

    @DeleteMapping("/{id}/deleteOaspete")
    public String deleteById(@PathVariable Integer id)
    {
        return oaspetiService.deleteOaspeteById(id);
    }

    @PutMapping("/{id}/updateOaspete")
    public String updateOaspete(@PathVariable Integer id,@RequestBody OaspeteDTOSimplu oaspeteDTOSimplu)
    {
        return oaspetiService.updateOaspete(id,oaspeteDTOSimplu);
    }
}
