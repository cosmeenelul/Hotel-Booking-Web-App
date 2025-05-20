package com.example.AdminDashboard.Controller;

import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.DTO.DetaliiRezervare;
import com.example.AdminDashboard.Service.CamereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class DisponibilitateController {

    @Autowired
    private CamereService camereService;

    @PostMapping("/verificaDisponibilitate")
    public List<CameraDTO> verificaDisponibilitate(@RequestBody DetaliiRezervare dto) {
        System.out.println("CheckIn: " + dto.getCheckIn());
        System.out.println("CheckOut: " + dto.getCheckOut());
        System.out.println("Persoane: " + dto.getPersoane());
        System.out.println(camereService.findAvailableRoomsByDatesAndCapacity(dto.getCheckIn(), dto.getCheckOut(), dto.getPersoane()));
        return camereService.findAvailableRoomsByDatesAndCapacity(dto.getCheckIn(), dto.getCheckOut(), dto.getPersoane());
    }

}
