using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Simulation
{
    public List<Road> roads = new();
    public int simulationWidth;
    public int simulationHeight;
    public int agentCount;
    public int scaleCoefficient;
    public int destinationRadius;
    public int timeQuantum;
    
    public Simulation()
    {
        this.roads = new List<Road>();
        this.simulationWidth = 0;
        this.simulationHeight = 0;
        this.agentCount = 0;
        this.scaleCoefficient = 0;
        this.destinationRadius = 0;
        this.timeQuantum = 0;
    }
    
    public void set(List<Road> roads, int simulationWidth, int simulationHeight, int agentCount, int scaleCoefficient, int destinationRadius, int timeQuantum)
    {
        this.roads = roads;
        this.simulationWidth = simulationWidth;
        this.simulationHeight = simulationHeight;
        this.agentCount = agentCount;
        this.scaleCoefficient = scaleCoefficient;
        this.destinationRadius = destinationRadius;
        this.timeQuantum = timeQuantum;
    }
    
    public void addRoad(Road road)
    {
        this.roads.Add(road);
    }
    
    public string getJson()
    {
        return JsonSerialization.ToJson(this);
    }
}
