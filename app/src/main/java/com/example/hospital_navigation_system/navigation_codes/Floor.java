package com.example.hospital_navigation_system.navigation_codes;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.widget.ImageView;

import com.example.hospital_navigation_system.R;

//import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
//import java.awt.image.*;
//import javax.imageio.*;
//import java.io.*;
//import java.awt.*;
import android.graphics.BitmapFactory;

public class Floor
{
    private int                 floorNumber;
    private Building            building;
    ArrayList<UpstairsVertex>   upstairs;
    ArrayList<DownstairsVertex> downstairs;
    Bitmap image;// = TestingVertex.imgview;
    ArrayList<Vertex>           vertices;
    Context context;

    public Floor( int floorNumber, Building building, String imagePath ) // NEVER USED IF YOU USE PLEASE CALL setImage() IN CONSTRUCTOR
    {
        vertices = new ArrayList<Vertex>();
        this.floorNumber = floorNumber;
        this.building = building;
        upstairs = new ArrayList<UpstairsVertex>();
        downstairs = new ArrayList<DownstairsVertex>();
    }

    public Floor(Context context, int floorNumber, Building building )
    {
        vertices = new ArrayList<Vertex>();
        this.floorNumber = floorNumber;
        this.building = building;
        upstairs = new ArrayList<UpstairsVertex>();
        downstairs = new ArrayList<DownstairsVertex>();
        this.context = context;
    }

    public void setImage( int floor ) // THIS PART CODED FOR ONLY B BUILDING. YOU MUST CHANGE THE CODE COMPLETELY, IF YOU WANT TO ADD NEW BUILDINGS, (Commented parts was for desktop version.)
    {
        if(floor == 0) {
            image = (Bitmap) BitmapFactory.decodeResource(context.getResources(), R.drawable.b_floor_0);//image.setImageResource(R.drawable.b_floor_0);
            image = Bitmap.createScaledBitmap(image, 1141, 600, true);
        }
        if(floor == 1) {
            image = (Bitmap) BitmapFactory.decodeResource(context.getResources(), R.drawable.b_floor_1);//image.setImageResource(R.drawable.b_floor_1);
            image = Bitmap.createScaledBitmap(image, 1148, 600, true);
        }
    }

    public Bitmap resizeImage(Bitmap image) {
        return image;
    }

    public int getFloorNumber()
    {
        return floorNumber;
    }

    public Bitmap getImage()//ImageView getImage()
    {
        return image;
    }

    public void addUpstairsVertex( UpstairsVertex usv)
    {
        upstairs.add( usv);
    }

    public void addDownstairsVertex( DownstairsVertex dsv)
    {
        downstairs.add( dsv);
    }

    public String toString()
    {
        return "Floor " + floorNumber;
    }

    public Building getBuilding()
    {
        return building;
    }

    public boolean equals( Floor other )
    {
        if ( this.getFloorNumber() == other.getFloorNumber()
                && this.getBuilding().equals( other.getBuilding() ) )
            return true;
        else
            return false;
    }


    public void addVertex( Vertex v)
    {
        vertices.add( v);
    }


}