package com.example.AdminDashboard.Entity;

import jakarta.persistence.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Entity
@Table(name="tblcamere")
public class Camera {
    @Id
    @Column(name="nrcamera")
    private Integer nrCamera;

    @Column(name="tipcamera")
    private String tipCamera;

    @Column(name="esteocupata")
    private Boolean esteOcupata;

    @Column(name="nrpersoanestandard")
    private Integer nrPersoaneStandard;

    @Column(name="pretpenoapte")
    private Double pretPeNoapte;

    @ManyToMany(mappedBy = "camere")
    private List<Rezervare> rezervari;

    public Camera(){}

    public Camera(Integer nrCamera, String tipCamera, Boolean esteOcupata, Integer nrPersoaneStandard, Double pretPeNoapte) {
        this.nrCamera = nrCamera;
        this.tipCamera = tipCamera;
        this.esteOcupata = esteOcupata;
        this.nrPersoaneStandard = nrPersoaneStandard;
        this.pretPeNoapte = pretPeNoapte;
    }

    @Override
    public String toString() {
        return "Camera{" +
                "nrCamera=" + nrCamera +
                ", tipCamera='" + tipCamera + '\'' +
                ", esteOcupata=" + esteOcupata +
                ", nrPersoaneStandard=" + nrPersoaneStandard +
                ", pretPeNoapte=" + pretPeNoapte +
                '}';
    }

    public void setTipCamera(String tipCamera) {
        this.tipCamera = tipCamera;
    }

    public void setEsteOcupata(Boolean esteOcupata) {
        this.esteOcupata = esteOcupata;
    }

    public void setNrPersoaneStandar(Integer nrPersoaneStandard) {
        this.nrPersoaneStandard = nrPersoaneStandard;
    }

    public void setPretPeNoapte(Double pretPeNoapte) {
        this.pretPeNoapte = pretPeNoapte;
    }

    public String getTipCamera() {
        return tipCamera;
    }

    public Double getPretPeNoapte() {
        return pretPeNoapte;
    }

    public Boolean getEsteOcupata() {
        return esteOcupata;
    }

    public Integer getNrPersoaneStandard() {
        return nrPersoaneStandard;
    }

    public void setNrCamera(Integer nrCamera) {
        this.nrCamera = nrCamera;
    }

    public Integer getNrCamera() {
        return nrCamera;
    }
}
