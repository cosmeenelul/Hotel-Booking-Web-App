package com.example.AdminDashboard.Entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tblrezervaricamere")
public class Rezervare {
    @Id
    private Integer nrCamera;

    public void setNrCamera(Integer nrCamera) {
        this.nrCamera = nrCamera;
    }

    public Integer getNrCamera() {
        return nrCamera;
    }
}
