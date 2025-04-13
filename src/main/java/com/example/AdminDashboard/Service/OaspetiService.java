package com.example.AdminDashboard.Service;

import com.example.AdminDashboard.Entity.Oaspete;
import com.example.AdminDashboard.Repository.OaspetiRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OaspetiService {

    private final OaspetiRepository oaspetiRepository;

    public OaspetiService(OaspetiRepository oaspetiRepository)
    {
        this.oaspetiRepository = oaspetiRepository;
    }

    public List<Oaspete> findAll()
    {
        return oaspetiRepository.findAll();
    }
}
