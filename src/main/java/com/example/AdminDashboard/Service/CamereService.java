package com.example.AdminDashboard.Service;

import com.example.AdminDashboard.Entity.Camera;
import com.example.AdminDashboard.Repository.CamereRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CamereService {

    CamereRepository camereRepository;

    public CamereService(CamereRepository camereRepository)
    {
        this.camereRepository = camereRepository;
    }

    public List<Camera> findAll()
    {
        return camereRepository.findAll();
    }
}
