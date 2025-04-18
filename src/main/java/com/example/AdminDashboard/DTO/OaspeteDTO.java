package com.example.AdminDashboard.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.List;

public class OaspeteDTO {

    private Integer idOaspete;
    private String nume;
    private String prenume;
    private String strada;
    private String oras;
    private String tara;
    private String telefon;
    private String email;

    @JsonManagedReference
    private List<RezervareResponseDTO> rezervari;

    public OaspeteDTO(){}

    public OaspeteDTO(Integer idOaspete, String nume, String strada, String tara, String telefon,
                      List<RezervareResponseDTO> rezervari, String oras, String prenume, String email) {
        this.idOaspete = idOaspete;
        this.nume = nume;
        this.strada = strada;
        this.tara = tara;
        this.telefon = telefon;
        this.rezervari = rezervari;
        this.oras = oras;
        this.prenume = prenume;
        this.email = email;
    }

    public Integer getIdOaspete() {
        return idOaspete;
    }

    public void setIdOaspete(Integer idOaspete) {
        this.idOaspete = idOaspete;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public List<RezervareResponseDTO> getRezervari() {
        return rezervari;
    }

    public void setRezervari(List<RezervareResponseDTO> rezervari) {
        this.rezervari = rezervari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
