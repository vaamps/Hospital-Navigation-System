package com.example.hospital_navigation_system.maps_floors;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.hospital_navigation_system.R;
import com.github.chrisbanes.photoview.PhotoView;
public class FirstFloor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_floor);

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view5);
        photoView.setImageResource(R.drawable.b_floor_1);
    }
}
