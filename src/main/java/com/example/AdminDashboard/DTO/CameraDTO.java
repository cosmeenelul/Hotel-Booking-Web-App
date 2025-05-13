package com.example.AdminDashboard.DTO;

public class CameraDTO {
    private Integer nrCamera;
    private String tipCamera;
    private Integer nrPersoaneStandard;
    private Double pretPeNoapte;
    private String linkImagine;

    public CameraDTO(){}

    public CameraDTO(Integer nrCamera, String tipCamera, Integer nrPersoaneStandard, Double pretPeNoapte, String linkImagine) {
        this.nrCamera = nrCamera;
        this.tipCamera = tipCamera;
        this.nrPersoaneStandard = nrPersoaneStandard;
        this.pretPeNoapte = pretPeNoapte;
        this.linkImagine=linkImagine;
    }


    public Integer getNrCamera() {
        return nrCamera;
    }

    public void setNrCamera(Integer nrCamera) {
        this.nrCamera = nrCamera;
    }

    public Integer getNrPersoaneStandard() {
        return nrPersoaneStandard;
    }

    public void setNrPersoaneStandard(Integer nrPersoaneStandard) {
        this.nrPersoaneStandard = nrPersoaneStandard;
    }

    public String getTipCamera() {
        return tipCamera;
    }

    public void setTipCamera(String tipCamera) {
        this.tipCamera = tipCamera;
    }

    public Double getPretPeNoapte() {
        return pretPeNoapte;
    }

    public void setPretPeNoapte(Double pretPeNoapte) {
        this.pretPeNoapte = pretPeNoapte;
    }

    public String getLinkImagine() {
        return linkImagine;
    }

    public void setLinkImagine(String linkImagine) {
        this.linkImagine = linkImagine;
    }
}
