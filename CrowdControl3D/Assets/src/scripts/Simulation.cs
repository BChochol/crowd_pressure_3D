using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Simulation
{
    public List<Road> roads = new();
    public List<AgentGroups> agentGroups = new();
    public int simulationWidth;
    public int simulationHeight;
    public int scaleCoefficient;
    public int destinationRadius;
    public int timeQuantum;
    
    public Simulation()
    {
        this.roads = new List<Road>();
        this.agentGroups = new List<AgentGroups>();
        this.simulationWidth = 0;
        this.simulationHeight = 0;
        this.scaleCoefficient = 0;
        this.destinationRadius = 0;
        this.timeQuantum = 0;
    }
    
    public void set(List<Road> roads, List<AgentGroups> agentGroups, int simulationWidth, int simulationHeight, int scaleCoefficient, int destinationRadius, int timeQuantum)
    {
        this.roads = roads;
        this.agentGroups = agentGroups;
        this.simulationWidth = simulationWidth;
        this.simulationHeight = simulationHeight;
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
