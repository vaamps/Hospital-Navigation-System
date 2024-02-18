package com.example.hospital_navigation_system.navigation_codes;

import android.content.Context;

public class DownstairsVertex extends Vertex implements Comparable<Vertex>
{
    Context context;
    public DownstairsVertex( Context context, String name, Floor floor, int x, int y )
    {
        super(context, name, floor, x, y);
        this.context = context;
    }
}
