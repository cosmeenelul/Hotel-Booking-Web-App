package com.example.AdminDashboard.Repository;

import com.example.AdminDashboard.Entity.Camera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface CamereRepository extends JpaRepository<Camera,Integer> {
}
