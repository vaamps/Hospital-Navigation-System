package com.example.hospital_navigation_system;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity()
public class DPLink {
    @PrimaryKey(autoGenerate = true)
    public int dpId;

    @ColumnInfo(name = "pId")
    public int pId;

    @ColumnInfo(name = "docId")
    public int docId;

    DPLink(){
        pId = 0;
        dpId = 0;
        docId = 0;
    }
}

