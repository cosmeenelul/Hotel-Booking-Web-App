package com.example.AdminDashboard.Controller;


import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.DTO.DetaliiRezervare;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Service.CamereService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "/camere")
public class CamereController {

    private final CamereService camereService;

    public CamereController(CamereService camereService) {
        this.camereService = camereService;
    }

    @GetMapping("/toateCamerele")
    public List<CameraDTO> findAll()
    {
        return camereService.findAll();
    }
    @GetMapping("/verificaDisponibilitate")
    public List<CameraDTO> verificaDisponibilitate(@RequestBody DetaliiRezervare detaliiRezervare)
    {
        return camereService.findAvailableRoomsByDatesAndCapacity(detaliiRezervare.getCheckIn(),detaliiRezervare.getCheckOut(),
                detaliiRezervare.getPersoane());
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

    @DeleteMapping("/{id}/stergeCamera")
    public String deleteCamera(@PathVariable Integer id)
    {
        return camereService.deleteById(id);
    }
}
