using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Simulation
{
    public List<Road> roads = new();
    public int simulationWidth = 0;
    public int simulationHeight = 0;
    public int agentCount = 0;
    public int scaleCoefficient = 0;
    public int destinationRadius = 0;
    public int timeQuantum = 0;
    
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
    
    public string getJson()
    {
        return JsonSerialization.ToJson(this);
    }
}
