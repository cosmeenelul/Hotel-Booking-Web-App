package com.example.AdminDashboard.Entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "tblrezervari")
public class Rezervare {

    // Coloane tabel

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idrezervare")
    private Integer idRezervare;

    @Column(name = "persoane")
    private Integer persoane;

    @Column(name = "datacheckin")
    private LocalDate dataCheckIn;

    @Column(name = "datacheckout")
    private LocalDate dataCheckOut;

    @Column(name = "total")
    private Double total;

    @Column(name ="codrezervare", unique = true)
    private String codRezervare;

    @ManyToMany
    @JoinTable(
            name = "tblrezervaricamere",
            joinColumns = @JoinColumn(name = "idrezervare"),
            inverseJoinColumns = @JoinColumn(name = "nrcamera")
    )
    private List<Camera> camere;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idplata")
    private Plata plata;

    @ManyToOne
//    @JsonBackReference
    @JoinColumn(name = "idoaspete")
    private Oaspete oaspete;

    public Rezervare(){}

    public Rezervare(Integer idRezervare, Integer persoane, LocalDate dataCheckIn, LocalDate dataCheckOut, Double total, List<Camera> camere, Plata plata, Oaspete oaspete) {
        this.idRezervare = idRezervare;
        this.persoane = persoane;
        this.dataCheckIn = dataCheckIn;
        this.dataCheckOut = dataCheckOut;
        this.total = total;
        this.camere = camere;
        this.plata = plata;
        this.oaspete = oaspete;
    }

    // Getters Setters


    public void setIdRezervare(Integer idRezervare) {
        this.idRezervare = idRezervare;
    }

    public Integer getIdRezervare() {
        return idRezervare;
    }

    public Integer getPersoane() {
        return persoane;
    }

    public void setPersoane(Integer persoane) {
        this.persoane = persoane;
    }

    public LocalDate getDataCheckOut() {
        return dataCheckOut;
    }

    public void setDataCheckOut(LocalDate dataCheckOut) {
        this.dataCheckOut = dataCheckOut;
    }

    public LocalDate getDataCheckIn() {
        return dataCheckIn;
    }

    public void setDataCheckIn(LocalDate dataCheckIn) {
        this.dataCheckIn = dataCheckIn;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public List<Camera> getCamere() {
        return camere;
    }

    public void setCamere(List<Camera> camere) {
        this.camere = camere;
    }

    public Oaspete getOaspete() {
        return oaspete;
    }

    public void setOaspete(Oaspete oaspete) {
        this.oaspete = oaspete;
    }

    public Plata getPlata() {
        return plata;
    }

    public void setPlata(Plata plata) {
        this.plata = plata;
    }


    public void addCamera(Camera camera)
    {
        this.camere.add(camera);
        camera.getRezervari().add(this);
    }

    public String getCodRezervare() {
        return codRezervare;
    }

    public void setCodRezervare(String codRezervare) {
        this.codRezervare = codRezervare;
    }
}
