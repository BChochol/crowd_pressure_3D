using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Road
{
    public float width = 1.0f;
    
    public Dictionary<string, float> start = new()
    {
        {"x", 0.0f},
        {"y", 0.0f}
    };

    public Dictionary<string, float> end = new()
    {
        {"x", 0.0f},
        {"y", 0.0f}
    };
    
    public Crossing crossing = new();
    
    public void set(float width, float startX, float startY, float endX, float endY, Crossing crossing)
    {
        this.width = width;
        this.start["x"] = startX;
        this.start["y"] = startY;
        this.end["x"] = endX;
        this.end["y"] = endY;
        this.crossing = crossing;
    }
    
    public Road get()
    {
        return this;
    }
}
