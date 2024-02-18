package com.example.hospital_navigation_system;



import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Scanner;


import com.example.hospital_navigation_system.navigation_codes.Building;
import com.example.hospital_navigation_system.navigation_codes.DownstairsVertex;
import com.example.hospital_navigation_system.navigation_codes.Edge;
import com.example.hospital_navigation_system.navigation_codes.Floor;
import com.example.hospital_navigation_system.navigation_codes.Navigation;
import com.example.hospital_navigation_system.navigation_codes.Room;
import com.example.hospital_navigation_system.navigation_codes.UpstairsVertex;
import com.example.hospital_navigation_system.navigation_codes.Vertex;



public class emergency_case extends AppCompatActivity {
    public static Room first = null;
    public static Room second = null;

    private boolean select1 = false;
    private boolean select2 = false;
    private Spinner spin;
    private Spinner spin2;
    private String firstOne;
    private String secondOne;
    private ArrayList<Room> roomList;
    public static Bitmap imgview;
    private int currentimage;
    int[] images = {R.drawable.b_floor_0, R.drawable.b_floor_1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_case);

        roomList = new ArrayList<Room>();

        Scanner scan = new Scanner(System.in);
        Building b = new Building(this, "B", 0, 2 );


        Floor b1 = b.getFloor( 1 );
        Floor b0 = b.getFloor( 0 );

        for( Floor f : b.getFloors() )
        {
            f.setImage(f.getFloorNumber());// you can change this path
        }


        // 0th floor ***********************************************************

        // defining vertices

        Vertex v_b0_1 = new Vertex(this,"Drop Off", b0, 430, 260);
        Vertex v_b0_2 = new Vertex(this,"Main Entrance Outside", b0, 540, 230);
        Vertex v_b0_3 = new Vertex(this,"Main Entrance Inside", b0, 600, 270);
        Vertex v_b0_4 = new Vertex(this,"Reception", b0, 580, 305);
        Vertex v_b0_5 = new Vertex(this,"Reception (2)", b0, 620, 315);
        Vertex v_b0_6 = new Vertex(this,"Reception Center", b0, 590, 340);
        Vertex v_b0_7 = new Vertex(this,"Pharmacy Consult", b0, 590, 390);
        Vertex v_b0_8 = new Vertex(this,"Pharmacy Wait", b0, 550, 390);
        Vertex v_b0_9 = new Vertex(this,"Dirty Utility", b0, 515, 390);
        Vertex v_b0_10 = new Vertex(this,"CE 10", b0, 460, 390);
        Vertex v_b0_11 = new Vertex(this,"CE Entrance", b0, 420, 380);
        Vertex v_b0_12 = new Vertex(this,"CE 9", b0, 380, 395);
        Vertex v_b0_13 = new Vertex(this,"CE 8", b0, 360, 400);
        Vertex v_b0_14 = new Vertex(this,"Counsel 1", b0, 295, 400);
        Vertex v_b0_15 = new Vertex(this,"CE 7", b0, 250, 400);
        Vertex v_b0_16 = new Vertex(this,"CE 6", b0, 230, 400);
        Vertex v_b0_17 = new Vertex(this,"CE 5", b0, 380, 365);
        Vertex v_b0_18 = new Vertex(this,"CE 4", b0, 360, 365);
        Vertex v_b0_19 = new Vertex(this,"Counsel 2", b0, 295, 360);
        Vertex v_b0_20 = new Vertex(this,"CE 3", b0, 250, 360);
        Vertex v_b0_21 = new Vertex(this,"CE 2", b0, 230, 360);
        Vertex v_b0_22 = new Vertex(this,"CE 1", b0, 150, 360);
        Vertex v_b0_23 = new Vertex(this,"Mid Area", b0, 650, 290);
        Vertex v_b0_24 = new Vertex(this,"Store", b0, 720, 290);
        Vertex v_b0_25 = new Vertex(this,"Macmillan Entry", b0, 720, 240);
        Vertex v_b0_26 = new Vertex(this,"Macmillan Therapy", b0, 730, 220);
        Vertex v_b0_27 = new Vertex(this,"WR 1", b0, 740, 290);
        Vertex v_b0_28 = new Vertex(this,"WR 2", b0, 780, 290);
        Vertex v_b0_29 = new Vertex(this,"Blood Test", b0, 780, 310);
        Vertex v_b0_30 = new Vertex(this,"Lobby Enter", b0, 830, 290);
        Vertex v_b0_31 = new UpstairsVertex(this,"Bed Lift", b0, 860, 290);
        Vertex v_b0_35 = new DownstairsVertex(this,"Bed Lift", b0, 860, 290);
        b0.addUpstairsVertex( (UpstairsVertex) v_b0_31 );
        b0.addDownstairsVertex( (DownstairsVertex) v_b0_35 );
        Vertex v_b0_32 = new Vertex(this,"Quiet Room", b0, 860, 220);
        Vertex v_b0_33 = new UpstairsVertex(this,"Stairs", b0, 860, 315);
        Vertex v_b0_36 = new DownstairsVertex(this,"Stairs", b0, 860, 315);
        b0.addUpstairsVertex( (UpstairsVertex) v_b0_33 );
        b0.addDownstairsVertex( (DownstairsVertex) v_b0_36 );
        Vertex v_b0_34 = new Vertex(this,"Tea Point", b0, 850, 330);
        Vertex v_b0_37 = new Vertex(this,"Drop Off Mid", b0, 485, 260);

        // setting the edges and their weights
        v_b0_1.addAdjacency( new Edge( v_b0_37, 15));
        v_b0_37.addAdjacency( new Edge( v_b0_2, 15));
        v_b0_2.addAdjacency( new Edge( v_b0_3, 15));
        v_b0_3.addAdjacency( new Edge( v_b0_4, 15));
        v_b0_3.addAdjacency( new Edge( v_b0_5, 15));
        v_b0_3.addAdjacency( new Edge( v_b0_23, 15));
        v_b0_5.addAdjacency( new Edge( v_b0_6, 15));
        v_b0_6.addAdjacency( new Edge( v_b0_7, 15));
        v_b0_7.addAdjacency( new Edge( v_b0_8, 15));
        v_b0_8.addAdjacency( new Edge( v_b0_9, 17));
        v_b0_9.addAdjacency( new Edge( v_b0_10, 15));
        v_b0_10.addAdjacency( new Edge( v_b0_11, 20));
        v_b0_11.addAdjacency( new Edge( v_b0_12, 15));
        v_b0_11.addAdjacency( new Edge( v_b0_17, 15));
        v_b0_12.addAdjacency( new Edge( v_b0_13, 10));
        v_b0_13.addAdjacency( new Edge( v_b0_14, 10));
        v_b0_14.addAdjacency( new Edge( v_b0_15, 10));
        v_b0_15.addAdjacency( new Edge( v_b0_16, 10));
        v_b0_16.addAdjacency( new Edge( v_b0_21, 23));
        v_b0_17.addAdjacency( new Edge( v_b0_18, 18));
        v_b0_18.addAdjacency( new Edge( v_b0_19, 10));
        v_b0_19.addAdjacency( new Edge( v_b0_20, 10));
        v_b0_20.addAdjacency( new Edge( v_b0_21, 10));
        v_b0_21.addAdjacency( new Edge( v_b0_22, 10));
        v_b0_23.addAdjacency( new Edge( v_b0_24, 20));
        v_b0_24.addAdjacency( new Edge( v_b0_25, 5));
        v_b0_25.addAdjacency( new Edge( v_b0_26, 20));
        v_b0_24.addAdjacency( new Edge( v_b0_27, 5));
        v_b0_27.addAdjacency( new Edge( v_b0_28, 5));
        v_b0_27.addAdjacency( new Edge( v_b0_29, 25));
        v_b0_28.addAdjacency( new Edge( v_b0_30, 25));
        v_b0_30.addAdjacency( new Edge( v_b0_31, 15));
        v_b0_31.addAdjacency( new Edge( v_b0_32, 25));
        v_b0_31.addAdjacency( new Edge( v_b0_33, 15));
        v_b0_30.addAdjacency( new Edge( v_b0_35, 15));
        v_b0_35.addAdjacency( new Edge( v_b0_32, 25));
        v_b0_35.addAdjacency( new Edge( v_b0_33, 15));
        v_b0_33.addAdjacency( new Edge( v_b0_34, 15));
        v_b0_31.addAdjacency( new Edge( v_b0_36, 15));
        v_b0_35.addAdjacency( new Edge( v_b0_36, 15));
        v_b0_36.addAdjacency( new Edge( v_b0_34, 15));
        v_b0_36.addAdjacency( new Edge( v_b0_33, 0));
        v_b0_31.addAdjacency( new Edge( v_b0_35, 0));

        // defining rooms
        Room ce1 = new Room( "CE 1", b0, v_b0_22);
        Room ce2 = new Room( "CE 2", b0, v_b0_21);
        Room ce3 = new Room( "CE 3", b0, v_b0_20);
        Room ce4 = new Room( "CE 4", b0, v_b0_18);
        Room ce5 = new Room( "CE 5", b0, v_b0_17);
        Room ce6 = new Room( "CE 6", b0, v_b0_16);
        Room ce7 = new Room( "CE 7", b0, v_b0_15);
        Room ce8 = new Room( "CE 8", b0, v_b0_13);
        Room ce9 = new Room( "CE 9", b0, v_b0_12);
        Room ce10 = new Room( "CE 10", b0, v_b0_10);
        Room pharmacy = new Room( "Pharmacy", b0, v_b0_7);
        Room macmillan_therapy = new Room( "Macmillan Therapy", b0, v_b0_26);
        Room washroom1 = new Room( "WashRoom 1", b0, v_b0_27);
        Room washroom2 = new Room( "WashRoom 2", b0, v_b0_28);
        Room bloodtest = new Room( "Blood Test", b0, v_b0_29);
        Room quietroom = new Room( "Quiet Room", b0, v_b0_32);
        Room mainentrance = new Room( "Main Entrance", b0, v_b0_2);

        roomList.add(ce1);
        roomList.add(ce2);
        roomList.add(ce3);
        roomList.add(ce4);
        roomList.add(ce5);
        roomList.add(ce6);
        roomList.add(ce7);
        roomList.add(ce8);
        roomList.add(ce9);
        roomList.add(ce10);
        roomList.add(pharmacy);
        roomList.add(macmillan_therapy);
        roomList.add(washroom1);
        roomList.add(washroom2);
        roomList.add(bloodtest);
        roomList.add(quietroom);
        roomList.add(mainentrance);



        // 1st floor ***********************************************************

        // defining vertices

        Vertex v_b1_1 = new UpstairsVertex(this,"B1 Bed Lift", b1, 850, 240);
        Vertex v_b1_2 = new DownstairsVertex(this,"B1 Bed Lift", b1, 850, 240);
        b1.addUpstairsVertex( (UpstairsVertex) v_b1_1 );
        b1.addDownstairsVertex( (DownstairsVertex) v_b1_2 );
        Vertex v_b1_3 = new UpstairsVertex(this,"B1 Stairs", b1, 850, 265);
        Vertex v_b1_4 = new DownstairsVertex(this,"B1 Stairs", b1, 850, 265);
        b1.addUpstairsVertex( (UpstairsVertex) v_b1_3 );
        b1.addDownstairsVertex( (DownstairsVertex) v_b1_4 );
        Vertex v_b1_5 = new Vertex(this,"Clinic Prep", b1, 780, 250);
        Vertex v_b1_6 = new Vertex(this,"Treatment Bed", b1, 850, 185);
        Vertex v_b1_7 = new Vertex(this,"Washroom 1", b1, 820, 160);
        Vertex v_b1_8 = new Vertex(this,"Tea Point B1", b1, 750, 270);
        Vertex v_b1_9 = new Vertex(this,"Washroom 2", b1, 680, 320);
        Vertex v_b1_10 = new Vertex(this,"Reception", b1, 780, 300);
        Vertex v_b1_11 = new Vertex(this,"Mid Reception", b1, 700, 350);
        Vertex v_b1_12 = new Vertex(this,"Staff Change Area", b1, 850, 375);
        Vertex v_b1_13 = new Vertex(this,"Main Treatment Area", b1, 750, 100);
        Vertex v_b1_14 = new Vertex(this,"Treatment Enter Area", b1, 650, 350);
        Vertex v_b1_15 = new Vertex(this,"Treatment Room 1", b1, 610, 360);
        Vertex v_b1_16 = new Vertex(this,"Counsel 3", b1, 580, 340);
        Vertex v_b1_17 = new Vertex(this,"Treatment Room 2", b1, 550, 360);
        Vertex v_b1_18 = new Vertex(this,"Counsel 4", b1, 525, 360);
        Vertex v_b1_19 = new Vertex(this,"Treatment Room 3", b1, 475, 355);
        Vertex v_b1_20 = new Vertex(this,"Mid Entry Area", b1, 440, 350);
        Vertex v_b1_21 = new Vertex(this,"Staff Area", b1, 415, 350);
        Vertex v_b1_22 = new Vertex(this,"Office 1", b1, 395, 350);
        Vertex v_b1_23 = new Vertex(this,"Office 2", b1, 375, 350);
        Vertex v_b1_24 = new Vertex(this,"Office 3", b1, 340, 350);
        Vertex v_b1_25 = new Vertex(this,"Meeting Room Mid", b1, 275, 335);
        Vertex v_b1_26 = new Vertex(this,"Meeting Room 1", b1, 300, 315);
        Vertex v_b1_27 = new Vertex(this,"Meeting Room 2", b1, 270, 315);
        Vertex v_b1_28 = new Vertex(this,"Meeting Room Mid", b1, 230, 320);


        // setting the edges and their weights
        v_b1_1.addAdjacency( new Edge( v_b1_3, 15));
        v_b1_2.addAdjacency( new Edge( v_b1_3, 15));
        v_b1_1.addAdjacency( new Edge( v_b1_4, 15));
        v_b1_2.addAdjacency( new Edge( v_b1_4, 15));
        v_b1_2.addAdjacency( new Edge( v_b1_1, 0));
        v_b1_3.addAdjacency( new Edge( v_b1_4, 0));
        v_b1_1.addAdjacency( new Edge( v_b1_6, 15));
        v_b1_2.addAdjacency( new Edge( v_b1_6, 15));
        v_b1_6.addAdjacency( new Edge( v_b1_7, 15));
        v_b1_7.addAdjacency( new Edge( v_b1_13, 28));
        v_b1_1.addAdjacency( new Edge( v_b1_5, 15));
        v_b1_2.addAdjacency( new Edge( v_b1_5, 15));
        v_b1_5.addAdjacency( new Edge( v_b1_8, 15));
        v_b1_5.addAdjacency( new Edge( v_b1_10, 15));
        v_b1_3.addAdjacency( new Edge( v_b1_10, 15));
        v_b1_4.addAdjacency( new Edge( v_b1_10, 15));
        v_b1_12.addAdjacency( new Edge( v_b1_10, 35));
        v_b1_10.addAdjacency( new Edge( v_b1_11, 30));
        v_b1_11.addAdjacency( new Edge( v_b1_9, 15));
        v_b1_11.addAdjacency( new Edge( v_b1_14, 15));
        v_b1_14.addAdjacency( new Edge( v_b1_15, 15));
        v_b1_15.addAdjacency( new Edge( v_b1_16, 15));
        v_b1_16.addAdjacency( new Edge( v_b1_17, 18));
        v_b1_17.addAdjacency( new Edge( v_b1_18, 15));
        v_b1_18.addAdjacency( new Edge( v_b1_19, 17));
        v_b1_19.addAdjacency( new Edge( v_b1_20, 10));
        v_b1_20.addAdjacency( new Edge( v_b1_21, 20));
        v_b1_21.addAdjacency( new Edge( v_b1_22, 15));
        v_b1_22.addAdjacency( new Edge( v_b1_23, 15));
        v_b1_23.addAdjacency( new Edge( v_b1_24, 15));
        v_b1_24.addAdjacency( new Edge( v_b1_25, 15));
        v_b1_25.addAdjacency( new Edge( v_b1_26, 15));
        v_b1_25.addAdjacency( new Edge( v_b1_27, 15));
        v_b1_25.addAdjacency( new Edge( v_b1_28, 15));

        // defining rooms
        Room treatment1 = new Room( "Treatment Room 1", b1, v_b1_15);
        Room treatment2 = new Room( "Treatment Room 2", b1, v_b1_17);
        Room treatment3 = new Room( "Treatment Room 3", b1, v_b1_19);
        Room staffroom = new Room( "B1 Staff Room", b1, v_b1_21);
        Room staffchangearea = new Room( "Staff Change Area", b1, v_b1_12);
        Room maintreatmentarea = new Room( "Main Treatment Area", b1, v_b1_13);
        Room washroom3 = new Room( "B1 Washroom 3", b1, v_b1_7);
        Room washroom4 = new Room( "B1 Washroom 4", b1, v_b1_9);
        Room b1cafe = new Room( "B1 Cafe", b1, v_b1_8);
        Room meetingroom1 = new Room( "Meeting Room 1", b1, v_b1_26);
        Room meetingroom2 = new Room( "Meeting Room 2", b1, v_b1_27);
        Room nurseoffice = new Room( "Nurse Office", b1, v_b1_23);
        Room hematologyoffice = new Room( "Hematology Office", b1, v_b1_28);

        roomList.add(treatment1);
        roomList.add(treatment2);
        roomList.add(treatment3);
        roomList.add(staffroom);
        roomList.add(staffchangearea);
        roomList.add(maintreatmentarea);
        roomList.add(washroom3);
        roomList.add(washroom4);
        roomList.add(b1cafe);
        roomList.add(meetingroom1);
        roomList.add(meetingroom2);
        roomList.add(nurseoffice);
        roomList.add(hematologyoffice);



        //////Change start and end points here////
        firstOne = "Main Entrance";
        secondOne = "Treatment Room 1";
        for(int i = 0; i < roomList.size(); i++) {
            if(firstOne.equals(roomList.get(i).getName()))
                first = roomList.get(i);
            if(secondOne.equals(roomList.get(i).getName()))
                second = roomList.get(i);
        }

        System.out.println(first.getName());
        System.out.println("First  :  " + first);
        System.out.println("Class  :  " + first.getClass().getName());
        System.out.println(second.getName());

        if( first != null && second != null) {
            if (true) {
                Scanner scan1 = new Scanner(System.in); //THIS COMMENTED PART IS UNNECESSARY IT IS HANDLED BY THE navigation_main2 class

                Context context = this;
                Navigation navi = new Navigation(context, first, second, scan1);
            } else
                System.out.println("No");
        }
        else
            Toast.makeText(getApplicationContext(),"Please select 2 locations.",Toast.LENGTH_SHORT).show();

    }

}

