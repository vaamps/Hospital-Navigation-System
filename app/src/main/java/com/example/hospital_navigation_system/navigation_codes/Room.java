package com.example.hospital_navigation_system.navigation_codes;

public class Room
{

    private Floor  floor;
    private Vertex vertex;
    private String name;

    public Room( String name, Floor floor, Vertex vertex)
    {
        this.name = name;
        this.floor = floor;
        this.vertex = vertex;
    }

    public Vertex getVertex()
    {
        return vertex;
    }

    public String toString()
    {
        return name;
    }

    public String getName() { return name; }


}
