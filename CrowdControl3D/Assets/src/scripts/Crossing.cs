using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Crossing
{
    [SerializeField] public float width = 1.0f;
    [SerializeField] public Dictionary<string, float> start = new()
    {
        {"x", 0.0f},
        {"y", 0.0f}
    };
    [SerializeField] public Dictionary<string, float> end = new()
    {
        {"x", 0.0f},
        {"y", 0.0f}
    };

    public void set(float width, float startX, float startY, float endX, float endY)
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
