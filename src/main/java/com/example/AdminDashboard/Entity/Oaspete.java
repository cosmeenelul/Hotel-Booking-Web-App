package com.example.AdminDashboard.Entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tbloaspeti")
public class Oaspete {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name="telefon", unique = true)
    private String telefon;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "oaspete", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Rezervare> rezervari;

    public Oaspete(){
        this.rezervari = new ArrayList<>();
    }

    public Oaspete(Integer idOaspete, String nume, String strada, String oras, String tara, String telefon,String email) {
        this.idOaspete = idOaspete;
        this.nume = nume;
        this.strada = strada;
        this.oras = oras;
        this.tara = tara;
        this.telefon = telefon;
        this.email = email;
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

    public List<Rezervare> getRezervari() {
        return rezervari;
    }

    public void setRezervari(List<Rezervare> rezervari) {
        this.rezervari = rezervari;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
