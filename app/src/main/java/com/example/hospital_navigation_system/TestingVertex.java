package com.example.hospital_navigation_system;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Scanner;
import com.example.hospital_navigation_system.navigation_codes.Navigation;
import com.example.hospital_navigation_system.navigation_codes.Room;

public class TestingVertex extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing_vertex);
        Room firstRoom = navigation_main2.first;
        Room secondRoom = navigation_main2.second;

        Scanner scan = new Scanner(System.in); //THIS COMMENTED PART IS UNNECESSARY IT IS HANDLED BY THE navigation_main2 class

        Context context = this;
        Navigation navi = new Navigation(context, firstRoom, secondRoom, scan);
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
        System.exit(0);
        finish();
    }

}
