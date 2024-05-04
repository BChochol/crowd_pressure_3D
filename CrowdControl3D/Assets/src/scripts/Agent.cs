using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Agent
{
    public float positionX;
    public float positionY;
    public float destinationX;
    public float destinationY;

    public Agent(float positionX, float positionY, float destinationX, float destinationY)
    {
        this.positionX = positionX;
        this.positionY = positionY;
        this.destinationX = destinationX;
        this.destinationY = destinationY;
    }
    
    public Agent get()
    {
        return this;
    }
}
