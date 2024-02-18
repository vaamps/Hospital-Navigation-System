package com.example.hospital_navigation_system;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Patient {
    @PrimaryKey(autoGenerate = true)
    public int pId;
    public String patientName;
    public int age;
    public int height;
    public int weight;
    public float heartRate;
    public float respRate;
    // Non Emergency Symptoms
    public float nausea;
    public float headache;
    public float diarrhea;
    public float soreThroat;
    public float cnc;

    // Emergency Symptoms
    public int covid;
    public int accident;
    public int stroke;
    public int allergies;

    public String spec;

    public Patient(){
        patientName = "";
        age = 0;
        height = 0;
        weight = 0;
        heartRate = 0.0f;
        respRate = 0.0f;
        nausea = 0.0f;
        headache = 0.0f;
        diarrhea = 0.0f;
        soreThroat = 0.0f;
        cnc = 0.0f;
        covid = 0;
        accident = 0;
        stroke = 0;
        allergies = 0;
        spec = "General";
    }

}

