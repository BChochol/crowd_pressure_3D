using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Road
{
    
    public string width;
    public Dictionary<string, string> start = new();
    public Dictionary<string, string> end = new();

    public Crossing crossing;
    
    public Road (string width, string startX, string startY, string endX, string endY, string cWidth, string cStartX, string cStartY, string cEndX, string cEndY)
    {
        this.width = width;
        this.start["x"] = startX;
        this.start["y"] = startY;
        this.end["x"] = endX;
        this.end["y"] = endY;
        this.crossing = new Crossing(cWidth, cStartX, cStartY, cEndX, cEndY);
    }

    public Road()
    {
        this.width = "0";
        this.start["x"] = "0";
        this.start["y"] = "0";
        this.end["x"] = "0";
        this.end["y"] = "0";
        this.crossing = new Crossing("0", "0", "0", "0", "0");
    }
    
    public Road get()
    {
        return this;
    }
}
