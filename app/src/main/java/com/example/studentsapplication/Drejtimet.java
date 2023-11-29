package com.example.studentsapplication;

public class Drejtimet {
    private int SimgResource;
    private String SDrejtimi;
    private String SPershkrimi;

    public  Drejtimet(int imgResource, String Drejtimi, String Pershkrimi){
       SimgResource=imgResource;
        SDrejtimi=Drejtimi;
        SPershkrimi=Pershkrimi;
    }

    public int getImgResource(){
        return SimgResource;
    }
    public String getDrejtimi(){
        return SDrejtimi;
    }
    public String getPershkrimi(){
        return SPershkrimi;
    }
}
