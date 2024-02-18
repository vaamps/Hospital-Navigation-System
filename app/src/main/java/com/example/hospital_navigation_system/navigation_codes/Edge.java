package com.example.hospital_navigation_system.navigation_codes;

public class Edge {
    final Vertex target;
    final double weight;

    public Edge( Vertex target, double weight)
    {
        this.target = target;
        this.weight = weight;
    }

    public Vertex getTarget()
    {
        return target;
    }

    public double getWeight()
    {
        return weight;
    }
}

