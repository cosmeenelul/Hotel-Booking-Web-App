package com.example.AdminDashboard.Converter;

import com.example.AdminDashboard.DTO.CameraDTO;
import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Entity.Oaspete;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CameraDTOConverter {

    @Autowired
    ModelMapper modelMapper;

    public CameraDTO convertCameraToCameraDTO(Camera camera)
    {
        return modelMapper.map(camera,CameraDTO.class);
    }

    public Camera convertCameraDTOToCamera(CameraDTO cameraDTO)
    {
        return modelMapper.map(cameraDTO,Camera.class);
    }

    public List<CameraDTO> convertListCameraToListCameraDTO(List<Camera> camere)
    {
        return camere
                .stream()
                .map(camera -> modelMapper.map(camera,CameraDTO.class))
                .collect(Collectors.toList());
    }

}
