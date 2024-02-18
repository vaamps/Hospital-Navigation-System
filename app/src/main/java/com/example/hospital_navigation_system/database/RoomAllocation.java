package com.example.hospital_navigation_system;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomAllocation {
    @PrimaryKey(autoGenerate = true)
    public int rId;

    public String roomCode;

    public String roomPurpose;

    public int isUse;

    public RoomAllocation(){
        roomCode = "";
        roomPurpose = "";
        isUse = 0;
    }

}
