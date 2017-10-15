package com.kass.khaddamni.khaddamni.entities;


public class Experience {
    String title;
    String description;
    String duree;
    String company;
    String idUser;

    public Experience(String title, String description, String duree, String company, String idUser) {
        this.title = title;
        this.description = description;
        this.duree = duree;
        this.company = company;
        this.idUser = idUser;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getCompany() {
        return company;
    }

    public String getDuree() {
        return duree;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
