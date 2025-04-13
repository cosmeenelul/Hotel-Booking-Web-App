package com.example.AdminDashboard.Entity;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tbloaspeti")
public class Oaspete {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="idoaspete")
    private Integer idOaspete;


    @Column(name="nume")
    private String nume;

    @Column(name="prenume")
    private String prenume;

    @Column(name="strada")
    private String strada;

    @Column(name="oras")
    private String oras;

    @Column(name="tara")
    private String tara;

    @Column(name="telefon")
    private String telefon;

    @ManyToMany(mappedBy = "oaspeti")
    private List<Rezervare> rezervari;

    public Oaspete(){}

    public Oaspete(Integer idOaspete, Integer idOaspete1, String nume, String strada, String oras, String tara, String telefon, String nume1) {
        this.idOaspete = idOaspete;
        this.nume = nume;
        this.strada = strada;
        this.oras = oras;
        this.tara = tara;
        this.telefon = telefon;
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

    public void setIdOaspete(Integer idOaspete) {
        this.idOaspete = idOaspete;
    }

    public Integer getIdOaspete() {
        return idOaspete;
    }
}
