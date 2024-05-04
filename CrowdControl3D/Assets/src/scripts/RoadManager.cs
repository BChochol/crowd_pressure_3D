using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using UnityEngine;

public class RoadManager : MonoBehaviour
{
    //private Simulation _simulation = new Simulation();
    public GameObject roadPrefab;
    public GameObject agentPrefab;
    public GameObject crossing;
    
    void Start()
    {
        Debug.Log(SimulationHandler.getJson());
        //setSimulation(SimulationHandler.simulation);
        //_simulation.roads.Add(new Road( "1", "-10.0", "-10.0", "10.0", "5.0", "2.0", "5.0", "-10.0", "-10.0", "10.0"));
        //_simulation.roads.Add(new Road("1.2", "-5.0", "8.0", "3.0", "-6.0", "1.0", "5.0", "-10.0", "-10.0", "10.0"));
        //_simulation.roads.Add(new Road("0.8", "0.0", "0.0", "6.0", "12.0", "0.8", "5.0", "-10.0", "-10.0", "10.0"));
        //_simulation.roads.Add(new Road("1.3", "-8.0", "4.0", "2.0", "10.0", "0.5", "5.0", "-10.0", "-10.0", "10.0"));
        
        initializeRoads();
        initializeAgents();
    }
    
    public void setSimulation(Simulation simulation)
    {
        //_simulation = simulation;
    }
     
    public void initializeRoads()
    {
        foreach (Road road in SimulationHandler.simulation.roads)
        {
            GameObject newRoad = Instantiate(roadPrefab, new Vector3(0, 0, 0), Quaternion.identity);
            GameObject newCrossing = Instantiate(crossing, new Vector3(0, 0.002f, 0), Quaternion.identity);

            
            Vector2 start = new Vector2(float.Parse(road.start["x"], CultureInfo.InvariantCulture.NumberFormat), float.Parse(road.start["y"], CultureInfo.InvariantCulture.NumberFormat));
            Vector2 end = new Vector2(float.Parse(road.end["x"], CultureInfo.InvariantCulture.NumberFormat), float.Parse(road.end["y"], CultureInfo.InvariantCulture.NumberFormat));
            Vector2 center = new Vector2((start.x + end.x) / 2, (start.y + end.y) / 2);
            float length = Mathf.Sqrt(Mathf.Pow((start.x-end.x), 2) + Mathf.Pow((start.y-end.y), 2));
            
            newRoad.transform.rotation = Quaternion.Euler(0, 90-Mathf.Atan2(end.y - start.y, end.x - start.x) * Mathf.Rad2Deg, 0);
            newRoad.transform.position = new Vector3(center.x, 0, center.y);
            newRoad.transform.localScale = new Vector3(float.Parse(road.width, CultureInfo.InvariantCulture.NumberFormat), newRoad.transform.localScale.y, length);
            
            float startCrossingX = float.Parse(road.crossing.start["x"], CultureInfo.InvariantCulture.NumberFormat);
            float startCrossingY = float.Parse(road.crossing.start["y"], CultureInfo.InvariantCulture.NumberFormat);
            float endCrossingX = float.Parse(road.crossing.end["x"], CultureInfo.InvariantCulture.NumberFormat);
            float endCrossingY = float.Parse(road.crossing.end["y"], CultureInfo.InvariantCulture.NumberFormat);
            float startRoadX = float.Parse(road.start["x"], CultureInfo.InvariantCulture.NumberFormat);
            float startRoadY = float.Parse(road.start["y"], CultureInfo.InvariantCulture.NumberFormat);
            float endRoadX = float.Parse(road.end["x"], CultureInfo.InvariantCulture.NumberFormat);
            float endRoadY = float.Parse(road.end["y"], CultureInfo.InvariantCulture.NumberFormat);

            float x = ((startCrossingX*endCrossingY - startCrossingY*endCrossingX)*(startRoadX-endRoadX) - (startRoadX*endRoadY - startRoadY*endRoadX)*(startCrossingX-endCrossingX))
                / ((startCrossingX-endCrossingX)*(startRoadY-endRoadY) - (startCrossingY-endCrossingY)*(startRoadX-endRoadX));
            
            float y = ((startCrossingX*endCrossingY - startCrossingY*endCrossingX)*(startRoadY-endRoadY) - (startRoadX*endRoadY - startRoadY*endRoadX)*(startCrossingY-endCrossingY))
                / ((startCrossingX-endCrossingX)*(startRoadY-endRoadY) - (startCrossingY-endCrossingY)*(startRoadX-endRoadX));


            float angle = Mathf.Abs(Mathf.Atan((endRoadY * (x - endCrossingX) + y * (endRoadX - endCrossingX) +
                                    endCrossingY * (x - endRoadX))/((endRoadX-x)*(x-endCrossingX)+(endRoadY-y)*(y-endCrossingY))) * Mathf.Rad2Deg);
            
            MaterialPropertyBlock block = new MaterialPropertyBlock();
            newCrossing.GetComponent<MeshRenderer>().GetPropertyBlock(block);
            block.SetFloat("_Skew", 90-angle);
            
            newCrossing.GetComponent<MeshRenderer>().SetPropertyBlock(block);
            
            newCrossing.transform.position = new Vector3(x, 0.002f, y);
            newCrossing.transform.localScale = new Vector3(float.Parse(road.width, CultureInfo.InvariantCulture.NumberFormat)/-10, 1f, float.Parse(road.crossing.width, CultureInfo.InvariantCulture.NumberFormat)/-10);
            newCrossing.transform.rotation = Quaternion.Euler(0.001f, 90-Mathf.Atan2(end.y - start.y, end.x - start.x) * Mathf.Rad2Deg, 0);
        }
    }
    
    public void initializeAgents()
    {
        Debug.Log(SimulationHandler.agents.Count);
        foreach (Agent agent in SimulationHandler.agents)
        {
            GameObject newAgent = Instantiate(agentPrefab, new Vector3(0, 0, 0), Quaternion.identity);
            newAgent.transform.position = new Vector3(agent.positionX, 0, agent.positionY);
        }
    }
}
