package com.example.AdminDashboard.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="tblcamere")
public class Camera {
    @Id
    @Column(name="nrcamera")
    private Integer nrCamera;

    @Column(name="tipcamera")
    private String tipCamera;

    @Column(name="nrpersoanestandard")
    private Integer nrPersoaneStandard;

    @Column(name="pretpenoapte")
    private Double pretPeNoapte;

    @ManyToMany(mappedBy = "camere")
    @JsonIgnore
    private List<Rezervare> rezervari;

    public Camera(){
        this.rezervari = new ArrayList<>();
    }

    public Camera(Integer nrCamera, String tipCamera, Integer nrPersoaneStandard, Double pretPeNoapte) {
        this.nrCamera = nrCamera;
        this.tipCamera = tipCamera;
        this.nrPersoaneStandard = nrPersoaneStandard;
        this.pretPeNoapte = pretPeNoapte;
    }

    public Integer getNrCamera() {
        return nrCamera;
    }

    public void setNrCamera(Integer nrCamera) {
        this.nrCamera = nrCamera;
    }

    public String getTipCamera() {
        return tipCamera;
    }

    public void setTipCamera(String tipCamera) {
        this.tipCamera = tipCamera;
    }

    public Integer getNrPersoaneStandard() {
        return nrPersoaneStandard;
    }

    public void setNrPersoaneStandard(Integer nrPersoaneStandard) {
        this.nrPersoaneStandard = nrPersoaneStandard;
    }

    public Double getPretPeNoapte() {
        return pretPeNoapte;
    }

    public void setPretPeNoapte(Double pretPeNoapte) {
        this.pretPeNoapte = pretPeNoapte;
    }

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void setRezervari(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }
}
