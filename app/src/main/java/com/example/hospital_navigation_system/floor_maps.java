package com.example.hospital_navigation_system;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


import com.example.hospital_navigation_system.maps_floors.EntranceFloor;
import com.example.hospital_navigation_system.maps_floors.FirstFloor;



public class floor_maps extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("fghjkkk");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_second);
        Spinner spin = (Spinner) findViewById(R.id.spinner2);
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("Please Select a Floor");
        list2.add("Ground Floor");
        list2.add("First Floor");

        ArrayAdapter<String> myAdapter2 = new ArrayAdapter<String>(floor_maps.this,
                android.R.layout.simple_list_item_1, list2);

        // myAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setOnItemSelectedListener(this);

        spin.setAdapter(myAdapter2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


        if ( position == 1){
            groundFloorViewer(view);
        }
        if ( position == 2){
            firstFloorViewer(view);
        }
    }


    public void groundFloorViewer ( View view ){
        Intent intent = new Intent(getApplicationContext(), EntranceFloor.class);
        startActivity(intent);
    }
    public void firstFloorViewer ( View view ){
        Intent intent = new Intent(getApplicationContext(), FirstFloor.class);
        startActivity(intent);
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}