package com.example.hospital_navigation_system.navigation_codes;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Integer;



public class Vertex implements Comparable<Vertex>
{
    public double minDistance = Double.POSITIVE_INFINITY;

    ArrayList<Edge> adjacencies;
    Vertex          previous;
    private int             x;
    private int             y;
    private String          name;
    private Floor           floor;
    private Context context;


    private int[] originalImageWidths = {1141, 1148};
    private int[] originalImageHeights = {600, 600};

    public Vertex(Context context, String name, Floor floor, int x, int y )
    {
        this.context = context;
        this.name = name;
        this.floor = floor;
        this.x = x;
        this.y = y;
        adjacencies = new ArrayList<Edge>();
        floor.addVertex( this );
    }

    public double computeDistance( Vertex other)
    {
        if ( getFloor().equals( other.getFloor() ) )
        {
            return Math.sqrt( (other.getY() - getY()) * (other.getY() - getY())
                    + (other.getX() - getX()) * (other.getX() - getX()) );
        }
        else // if on different floors
            return Integer.MAX_VALUE;
    }

    public boolean equals( Vertex other)
    {
        return (getX() == other.getX()) && (getY() == other.getY())
                && (getFloor().equals(other.getFloor()));
    }

    public Vertex findClosestVertex()
    {
        Floor f = getFloor();
        Vertex closest;
        double minDistance;

        closest = f.vertices.get(0);
        minDistance = computeDistance(closest);
        for ( int i = 1; i < f.vertices.size(); i++ )
        {
            if ( ! f.vertices.get(i).equals( this ) // so as to not count itself
                    && computeDistance( f.vertices.get(i) ) < minDistance )
            {
                closest = f.vertices.get(i);
                minDistance = computeDistance( f.vertices.get(i) );
            }
        }
        return closest;
    }


    public String toString()
    {
        return name;
    }

    public Floor getFloor()
    {
        return floor;
    }

    public int getY()
    {
        Drawable d = new BitmapDrawable(context.getResources(), floor.getImage());
        int height = d.getIntrinsicHeight();
        return height / originalImageHeights[ floor.getFloorNumber() - floor.getBuilding().getFloors().get(0).getFloorNumber() ] * y;//floor.getImage().getHeight() / originalImageHeights[ floor.getFloorNumber() - floor.getBuilding().getFloors().get(0).getFloorNumber() ] * y;
    }

    public int getX()
    {
        Drawable d = new BitmapDrawable(context.getResources(), floor.getImage());
        int width = d.getIntrinsicWidth();
        return width / originalImageWidths[ floor.getFloorNumber() - floor.getBuilding().getFloors().get(0).getFloorNumber() ] * x;//floor.getImage().getWidth() / originalImageWidths[ floor.getFloorNumber() - floor.getBuilding().getFloors().get(0).getFloorNumber() ] * x;
    }

    public int compareTo( Vertex other)
    {
        return Double.compare( minDistance, other.minDistance );
    }

    public void addAdjacency( Edge edge)
    {
        adjacencies.add( edge);
        edge.getTarget().adjacencies.add( new Edge( this, edge.getWeight() ) );
    }

}
