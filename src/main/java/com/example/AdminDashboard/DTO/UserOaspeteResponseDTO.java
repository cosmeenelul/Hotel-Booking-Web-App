package com.example.AdminDashboard.DTO;

public class UserOaspeteResponseDTO {

    private Integer idOaspete;
    private String nume;
    private String prenume;
    private String strada;
    private String oras;
    private String tara;
    private String email;
    private String telefon;

    public UserOaspeteResponseDTO(String email, Integer idOaspete, String oras, String strada, String tara, String prenume, String nume, String telefon) {
        this.email = email;
        this.idOaspete = idOaspete;
        this.oras = oras;
        this.strada = strada;
        this.tara = tara;
        this.prenume = prenume;
        this.nume = nume;
        this.telefon = telefon;
    }

    public UserOaspeteResponseDTO() {
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Integer getIdOaspete() {
        return idOaspete;
    }

    public void setIdOaspete(Integer idOaspete) {
        this.idOaspete = idOaspete;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOras() {
        return oras;
    }

    public void setOras(String oras) {
        this.oras = oras;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getTara() {
        return tara;
    }

    public void setTara(String tara) {
        this.tara = tara;
    }

    public String getStrada() {
        return strada;
    }

    public void setStrada(String strada) {
        this.strada = strada;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }
}
