package com.example.hospital_navigation_system.navigation_codes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

    public class Dijkstra {

        public static void computePaths( Vertex source)
        {
            source.minDistance = 0.;
            PriorityQueue<Vertex> vertexQueue = new PriorityQueue<Vertex>();
            vertexQueue.add( source);

            while ( !vertexQueue.isEmpty() )
            {
                Vertex u = vertexQueue.poll();

                // Visit each edge exiting u
                for ( Edge e : u.adjacencies )
                {
                    Vertex v;
                    double weight;
                    double distanceThroughU;

                    v = e.target;
                    weight = e.weight;
                    distanceThroughU = u.minDistance + weight;

                    if (distanceThroughU < v.minDistance )
                    {
                        vertexQueue.remove(v);
                        v.minDistance = distanceThroughU ;
                        v.previous = u;
                        vertexQueue.add(v);
                    }
                }
            } // end of while
        } // end of computePaths()


        public static List<Vertex> getShortestPathTo(Vertex target)
        {
            List<Vertex> path;
            path = new ArrayList<Vertex>();

            for ( Vertex vertex = target; vertex != null; vertex = vertex.previous )
            {
                path.add( vertex);
            }

            Collections.reverse( path);
            return path;
        }

    }
