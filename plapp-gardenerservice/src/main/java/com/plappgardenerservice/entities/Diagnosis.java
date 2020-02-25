package com.plappgardenerservice.entities;

import org.springframework.data.annotation.Id;

import javax.persistence.Entity;

@Entity
public class Diagnosis {

    @Id
    private String plantID;
    private boolean ill;
    private String disease;

    public Diagnosis(){}

    public Diagnosis(String plantID, boolean ill, String disease) {
        this.plantID = plantID;
        this.ill = ill;
        this.disease = disease;
    }

    @javax.persistence.Id
    public String getPlantID() {
        return plantID;
    }

    public void setPlantID(String plantID) {
        this.plantID = plantID;
    }

    public boolean isIll() {
        return ill;
    }

    public void setIll(boolean ill) {
        this.ill = ill;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        return "Diagnosis{" +
                "plantID='" + plantID + '\'' +
                ", ill=" + ill +
                ", disease='" + disease + '\'' +
                '}';
    }
}
