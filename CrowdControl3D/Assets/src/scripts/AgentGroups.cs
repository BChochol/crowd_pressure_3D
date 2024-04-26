using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AgentGroups
{
    
    public Dictionary<string, string> startCenter = new();
    public string startRadius;
    public Dictionary<string, string> destination = new();
    public string destinationRadius;
    public string groupSize;
    
    public AgentGroups(string startCenterX, string startCenterY, string startRadius, string destinationX, string destinationY, string destinationRadius, string groupSize)
    {
        this.startCenter["x"] = startCenterX;
        this.startCenter["y"] = startCenterY;
        this.startRadius = startRadius;
        this.destination["x"] = destinationX;
        this.destination["y"] = destinationY;
        this.destinationRadius = destinationRadius;
        this.groupSize = groupSize;
    }

    public AgentGroups()
    {
        this.startCenter["x"] = "0";
        this.startCenter["y"] = "0";
        this.startRadius = "0";
        this.destination["x"] = "0";
        this.destination["y"] = "0";
        this.destinationRadius = "0";
        this.groupSize = "0";
    }
    
    public AgentGroups get()
    {
        return this;
    }
}