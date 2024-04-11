using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using UnityEngine;

public class RoadManager : MonoBehaviour
{
    private Simulation _simulation = new Simulation();
    public GameObject myPrefab;
    void Start()
    {
        //setSimulation(SimulationHandler.simulation);
        _simulation.roads.Add(new Road( "0.7", "1.0", "1.0", "4.0", "5.0", "0.0", "0.0", "0.0", "0.0", "0.0"));
        initializeRoads();
    }
    
    public void setSimulation(Simulation simulation)
    {
        _simulation = simulation;
    }

    public void initializeRoads()
    {
        foreach (Road road in _simulation.roads)
        {
            GameObject newRoad = Instantiate(myPrefab, new Vector3(0, 0, 0), Quaternion.identity);
            
            Debug.Log(float.Parse(road.start["x"], CultureInfo.InvariantCulture.NumberFormat));
            
            Vector2 start = new Vector2(float.Parse(road.start["x"], CultureInfo.InvariantCulture.NumberFormat), float.Parse(road.start["y"], CultureInfo.InvariantCulture.NumberFormat));
            Vector2 end = new Vector2(float.Parse(road.end["x"], CultureInfo.InvariantCulture.NumberFormat), float.Parse(road.end["y"], CultureInfo.InvariantCulture.NumberFormat));
            Vector2 center = new Vector2((start.x + end.x) / 2, (start.y + end.y) / 2);
            float length = Mathf.Sqrt(Mathf.Pow((start.x-end.x), 2) + Mathf.Pow((start.y-end.y), 2));
            
            newRoad.transform.position = new Vector3(center.x, 0, center.y);
            newRoad.transform.localScale = new Vector3(newRoad.transform.localScale.x, newRoad.transform.localScale.y, float.Parse(road.width, CultureInfo.InvariantCulture.NumberFormat));
            newRoad.transform.localScale = new Vector3(newRoad.transform.localScale.x, newRoad.transform.localScale.y, length);            
            newRoad.transform.rotation = Quaternion.Euler(0, Mathf.Atan2(end.y - start.y, end.x - start.x) * Mathf.Rad2Deg, 0);
        }
    }
}
