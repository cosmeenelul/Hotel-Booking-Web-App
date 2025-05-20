package com.example.AdminDashboard.DTO;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.time.LocalDate;
import java.util.List;

public class RezervareResponseDTO {

    private Integer idRezervare;
    private Integer persoane;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private List<CameraDTO> camere;
    private PlataDTO plata;
    private Double total;
    private String codRezervare;
    private String telefon;
    @JsonManagedReference
    private OaspeteDTOSimplu oaspeteDTOSimplu;
    public RezervareResponseDTO(){}

    public RezervareResponseDTO(Integer persoane, LocalDate checkIn, List<CameraDTO> camere, LocalDate checkOut, PlataDTO plata, Double total,String codRezervare
    ,OaspeteDTOSimplu oaspeteDTOSimplu, String telefon) {
        this.persoane = persoane;
        this.checkIn = checkIn;
        this.camere = camere;
        this.checkOut = checkOut;
        this.plata = plata;
        this.total = total;
        this.codRezervare = codRezervare;
        this.oaspeteDTOSimplu = oaspeteDTOSimplu;
        this.telefon = oaspeteDTOSimplu.getTelefon();
    }

    public int getPersoane() {
        return persoane;
    }

    public void setPersoane(int persoane) {
        this.persoane = persoane;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public List<CameraDTO> getCamere() {
        return camere;
    }

    public void setCamere(List<CameraDTO> camere) {
        this.camere = camere;
    }

    public PlataDTO getPlata() {
        return plata;
    }
    public void setPlata(PlataDTO plata) {
        this.plata = plata;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Integer getIdRezervare() {
        return idRezervare;
    }

    public void setIdRezervare(Integer idRezervare) {
        this.idRezervare = idRezervare;
    }

    public void setPersoane(Integer persoane) {
        this.persoane = persoane;
    }

    public String getCodRezervare() {
        return codRezervare;
    }

    public void setCodRezervare(String codRezervare) {
        this.codRezervare = codRezervare;
    }

    public OaspeteDTOSimplu getOaspeteDTOSimplu() {
        return oaspeteDTOSimplu;
    }

    public void setOaspeteDTO(OaspeteDTOSimplu oaspeteDTOSimplu) {
        this.oaspeteDTOSimplu = oaspeteDTOSimplu;
    }

    public void setOaspeteDTOSimplu(OaspeteDTOSimplu oaspeteDTOSimplu) {
        this.oaspeteDTOSimplu = oaspeteDTOSimplu;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
