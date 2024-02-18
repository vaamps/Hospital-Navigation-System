package com.example.hospital_navigation_system;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Doctor {
    @PrimaryKey(autoGenerate = true)
    public int docId;
    public String doctorName;
    public int isOnCall;
    public String specialization;

    public Doctor(){
        doctorName = "";
        isOnCall = 1;
        specialization = "General";
    }

    public Doctor(String doctorName, int isOnCall, String specialization){
        this.doctorName = doctorName;
        this.isOnCall = isOnCall;
        this.specialization = specialization;
    }
}
