package com.example.AdminDashboard.Service;

import com.example.AdminDashboard.Converter.CameraDTOConverter;
import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Exception.GlobalException;
import com.example.AdminDashboard.Repository.CamereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class CamereService {

    @Autowired
    CameraDTOConverter cameraDTOConverter;

    private final CamereRepository camereRepository;

    public CamereService(CamereRepository camereRepository)
    {
        this.camereRepository = camereRepository;
    }

    // GET ALL METHOD

    public List<CameraDTO> findAll()
    {
        return cameraDTOConverter.convertListCameraToListCameraDTO(camereRepository.findAll());
    }

    // GET BY ID

    public CameraDTO findById(Integer id){
        Camera camera = camereRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camera nu a fost gasita"));
        return cameraDTOConverter.convertCameraToCameraDTO(camera);
    }

    // GET TIPURI DE CAMERE

    public List<String> findDistinctRoomTypes(){
        return camereRepository.findDistinctRoomTypes();
    }

    // (GET) VERIFICARE DISPONIBILITATE

    public List<CameraDTO> findAvailableRoomsByDatesAndCapacity(LocalDate checkIn, LocalDate checkOut, Integer persoane){
        List<Camera> camere = camereRepository.findAvailableRoomsByDatesAndCapacity(checkIn,checkOut,persoane)
                .orElseThrow(() -> new GlobalException("Nu exista camere libere in acel inteval sau nu exista camere cu acest numar de persoane"));
        
        return cameraDTOConverter.convertListCameraToListCameraDTO(camere);
    }
}
