package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.RezervareCreateDTO;
import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Entity.Rezervare;
import com.example.AdminDashboard.DTO.DetaliiRezervare;
import com.example.AdminDashboard.Service.RezervariService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/rezervari")
public class RezervariController {

    private final RezervariService rezervariService;

    public RezervariController(RezervariService rezervariService)
    {
        this.rezervariService = rezervariService;
    }


    @GetMapping("/toateRezervarile")
    public List<RezervareResponseDTO> findAll()
    {
        return rezervariService.findAll();
    }

    @GetMapping("/{id}")
    public RezervareResponseDTO findById(@PathVariable Integer id)
    {
        return rezervariService.findById(id);
    }


    @PostMapping("/addRezervare")
    public RezervareCreateDTO createRezervare(@RequestBody RezervareCreateDTO rezervareCreateDTO)
    {
        return rezervariService.saveRezervare(rezervareCreateDTO);
    }

    @PutMapping("/{id}/updateRezervare")
    public void updateRezervare(@PathVariable Integer id, @RequestBody RezervareCreateDTO rezervareCreateDTO)
    {
        rezervariService.updateRezervare(id,rezervareCreateDTO);
    }


    @DeleteMapping("/{id}/deleteRezervare")
    public String deleteRezervareById(@PathVariable Integer id)
    {
        return rezervariService.deleteById(id);
    }
}

