package com.example.hospital_navigation_system.navigation_codes;

import android.content.Context;

import java.util.ArrayList;

public class Building {

    private ArrayList<Floor> floors;
    private String           name;
    Context context;

    public Building( Context context, String name, int startingFloor, int finalFloor)
    {
        this.name = name;
        this.context = context;
        floors = new ArrayList<Floor>();
        for( int i = startingFloor; i < finalFloor; i++ )
        {
            floors.add( new Floor(context, i, this ) );
        }
    }

    public ArrayList<Floor> getFloors()
    {
        return floors;
    }

    public String toString()
    {
        return name + " Building";
    }

    public String getName()
    {
        return name;
    }

    public Floor getFloor( int n)
    {
        for ( int i = 0; i < floors.size(); i++ )
        {
            if ( n == floors.get(i).getFloorNumber() )
                return floors.get(i);
        }
        return null;
    }

    public boolean equals( Building other )
    {
        if ( this.getName().equals( other.getName() ) )
            return true;
        else
            return false;
    }

}