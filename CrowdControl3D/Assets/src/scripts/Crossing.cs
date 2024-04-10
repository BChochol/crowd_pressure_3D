using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Crossing
{
    [SerializeField] public string width;
    [SerializeField] public Dictionary<string, string> start = new();
    [SerializeField] public Dictionary<string, string> end = new();
    
    public Crossing(string width, string startX, string startY, string endX, string endY)
    {
        this.width = width;
        this.start["x"] = startX;
        this.start["y"] = startY;
        this.end["x"] = endX;
        this.end["y"] = endY;
    }
    
    public Crossing()
    {
        this.width = "0";
        this.start["x"] = "0";
        this.start["y"] = "0";
        this.end["x"] = "0";
        this.end["y"] = "0";
    }

    public void set(string width, string startX, string startY, string endX, string endY)
    {
        this.width = width;
        this.start["x"] = startX;
        this.start["y"] = startY;
        this.end["x"] = endX;
        this.end["y"] = endY;
    }

    public Crossing get()
    {
        return this;
    }
}
