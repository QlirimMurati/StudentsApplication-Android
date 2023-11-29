package com.example.studentsapplication;

public class UploadNjoftime {
    String njoftimiLenda;
    String njoftimiPershkrimi;
    String njoftimiData;
    String njoftimiType;
    String njoftimiUrl;
    String njoftimiID;


    public UploadNjoftime(){}

    public UploadNjoftime( String njoftimiLenda, String njoftimiPershkrimi, String njoftimiData, String njoftimiType,String njoftimiUrl,String njoftimiID){
        this.njoftimiLenda=njoftimiLenda;
        this.njoftimiPershkrimi=njoftimiPershkrimi;
        this.njoftimiData=njoftimiData;
        this.njoftimiType=njoftimiType;
        this.njoftimiUrl=njoftimiUrl;
        this.njoftimiID=njoftimiID;
    }

    public String getNjoftimiLenda() {
        return njoftimiLenda;
    }

    public String getNjoftimiPershkrimi() {
        return njoftimiPershkrimi;
    }

    public String getNjoftimiData() {
        return njoftimiData;
    }

    public String getNjoftimiType() {
        return njoftimiType;
    }

    public String getNjoftimiUrl() {
        return njoftimiUrl;
    }

    public String getNjoftimiID() {
        return njoftimiID;
    }

    public void setNjoftimiLenda(String njoftimiLenda) {
        this.njoftimiLenda = njoftimiLenda;
    }

    public void setNjoftimiPershkrimi(String njoftimiPershkrimi) {
        this.njoftimiPershkrimi = njoftimiPershkrimi;
    }

    public void setNjoftimiData(String njoftimiData) {
        this.njoftimiData = njoftimiData;
    }

    public void setNjoftimiType(String njoftimiType) {
        this.njoftimiType = njoftimiType;
    }

    public void setNjoftimiUrl(String njoftimiUrl) {
        this.njoftimiUrl = njoftimiUrl;
    }

    public void setNjoftimiID(String njoftimiID) {
        this.njoftimiID = njoftimiID;
    }
}
