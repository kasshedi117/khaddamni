package com.kass.khaddamni.khaddamni.entities;

import java.io.Serializable;


public class Offre implements Serializable {
    int id;
    String titre;
    String descrition;
    String place;
    String date;
    String idUser;


    public Offre()
    {
        super();
    }

    public Offre(int id,String titre, String descrition, String place, String date, String idUser) {
        this.id=id;
        this.titre = titre;
        this.descrition = descrition;
        this.place = place;
        this.date = date;
        this.idUser=idUser;
    }


    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getDescrition() {
        return descrition;
    }

    public String getPlace() {
        return place;
    }

    public String getDate() {
        return date;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setDescrition(String descrition) {
        this.descrition = descrition;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDate(String domaine) {
        this.date = date;
    }
}
