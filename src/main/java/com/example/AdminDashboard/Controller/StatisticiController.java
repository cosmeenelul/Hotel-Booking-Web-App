package com.example.AdminDashboard.Controller;

import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.DTO.OaspeteDTOSimplu;
import com.example.AdminDashboard.DTO.RezervareResponseDTO;
import com.example.AdminDashboard.DTO.TopCamereTotalDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Service.CamereService;
import com.example.AdminDashboard.Service.OaspetiService;
import com.example.AdminDashboard.Service.RezervariService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/statistici")
public class StatisticiController {

    @Autowired
    private OaspetiService oaspetiService;

    @Autowired
    private CamereService camereService;



    @GetMapping
    public String getStatistici(Model model) {
        // Lista de oaspeți fideli
        List<OaspeteDTOSimplu> clientiFideli = oaspetiService.findLoyalGuestsWithAtLeastThreeReservations();
        model.addAttribute("clientiFideli", clientiFideli);

        // Lista celor mai rezervate camere
        List<Object[]> camereTotal = camereService.findTop3Camere();
        List<Integer> nrCamere = new ArrayList<>();
        List<Double> totalCamere = new ArrayList<>();

        for (Object[] objects : camereTotal) {
            Integer nrCamera = (Integer) objects[0];
            Double total = (Double) objects[1];

            nrCamere.add(nrCamera);
            totalCamere.add(total);
        }

        List<CameraDTO> camere = new ArrayList<>();
        camere.add(camereService.findById(nrCamere.get(0)));
        camere.add(camereService.findById(nrCamere.get(1)));
        camere.add(camereService.findById(nrCamere.get(2)));

        List<Double> totaluri = new ArrayList<>();
        totaluri.add(totalCamere.get(0));
        totaluri.add(totalCamere.get(1));
        totaluri.add(totalCamere.get(2));

        List<TopCamereTotalDTO> topCamereTotalDTOS = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TopCamereTotalDTO topCamereTotalDTO = new TopCamereTotalDTO(totaluri.get(i), camere.get(i));
            topCamereTotalDTOS.add(topCamereTotalDTO);
        }

        model.addAttribute("camereTop", topCamereTotalDTOS);


        return "statistici";
    }


}
