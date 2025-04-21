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
                .orElseThrow(() -> new GlobalException("Nu exista camere libere in acel inteval"));
        
        return cameraDTOConverter.convertListCameraToListCameraDTO(camere);
    }

    public String deleteById(Integer id)
    {
        camereRepository.deleteById(id);
        return "Camera a fost stearsa cu succes!";
    }

    public CameraDTO updateCamera(Integer id, CameraDTO updatedCamera)
    {
        Camera oldCamera = camereRepository.findById(id).orElseThrow(() -> new GlobalException("Camera nu exista!"));

        oldCamera.setTipCamera(updatedCamera.getTipCamera());
        oldCamera.setNrPersoaneStandard(updatedCamera.getNrPersoaneStandard());
        oldCamera.setPretPeNoapte(updatedCamera.getPretPeNoapte());

        camereRepository.save(oldCamera);

        return updatedCamera;
    }

    public String saveCamera(CameraDTO cameraDTO)
    {
        camereRepository.save(cameraDTOConverter.convertCameraDTOToCamera(cameraDTO));
        return "Camera adaugata cu succes!";
    }

}
