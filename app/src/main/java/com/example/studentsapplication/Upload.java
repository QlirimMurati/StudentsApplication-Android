package com.example.studentsapplication;

import static android.os.Build.ID;

public class Upload {
    public String emriMateriali;
    public String dateMateriali;
    public String typeMateriali;
    public String urlMateriali;
    public String idMateriali;

    public Upload() {
    }

    public Upload(String emriMateriali, String dateMateriali,String typeMateriali, String urlMateriali,String idMateriali) {
         this.emriMateriali = emriMateriali;
         this.dateMateriali = dateMateriali;
        this.typeMateriali = typeMateriali;
        this.urlMateriali = urlMateriali;
        this.idMateriali=idMateriali;
    }

    public String getIdMateriali() {
        return idMateriali;
    }
    public String getEmriMateriali() {
        return emriMateriali;
    }
    public String getDateMateriali() {
        return dateMateriali;
    }
    public String getTypeMateriali() {
        return typeMateriali;
    }
    public String getUrlMateriali() {
        return urlMateriali;
    }

    // nuk nevojiten per momentin
    public void setEmriMateriali(String emriMateriali) {
        this.emriMateriali = emriMateriali;
    }
    public void setDateMateriali(String dateMateriali) {
        this.dateMateriali = dateMateriali;
    }
    public void setTypeMateriali(String typeMateriali) {
        this.typeMateriali = typeMateriali;
    }
    public void setUrlMateriali(String urlMateriali) {
        this.urlMateriali = urlMateriali;
    }
    public void setIdMateriali(String idMateriali) {
        this.idMateriali = idMateriali;
    }
}
