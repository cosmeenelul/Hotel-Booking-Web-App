package com.example.AdminDashboard.DTO;

import jakarta.validation.constraints.NotBlank;

public class OaspeteDTOSimplu {

    private Integer idOaspete;
    private String nume;
    private String prenume;
    private String strada;
    private String oras;
    private String tara;

    @NotBlank(message = "Email-ul este obligatoriu")
    private String telefon;

    @NotBlank(message = "Email-ul este obligatoriu")
    private String email;

    @NotBlank(message = "Email-ul este obligatoriu")
    private String parola;

    public OaspeteDTOSimplu(){}

    public OaspeteDTOSimplu(Integer idOaspete, String nume, String prenume, String strada, String tara, String oras, String telefon, String email,
                            String parola, String rol) {
        this.idOaspete = idOaspete;
        this.nume = nume;
        this.prenume = prenume;
        this.strada = strada;
        this.tara = tara;
        this.oras = oras;
        this.telefon = telefon;
        this.email = email;
        this.parola = parola;
    }

    public Integer getIdOaspete() {
        return idOaspete;
    }

    public void setIdOaspete(Integer idOaspete) {
        this.idOaspete = idOaspete;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}
