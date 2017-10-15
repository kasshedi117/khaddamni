package com.kass.khaddamni.khaddamni.entities;


public class Applications {

    int id;
    int idOffre;
    int idUser;
    int etat;

    public Applications(int id, int idOffre, int idUser, int etat) {
        this.id = id;
        this.idOffre = idOffre;
        this.idUser = idUser;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public int getIdOffre() {
        return idOffre;
    }

    public int getIdUser() {
        return idUser;
    }

    public int getEtat() {
        return etat;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setIdOffre(int idOffre) {
        this.idOffre = idOffre;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
}
