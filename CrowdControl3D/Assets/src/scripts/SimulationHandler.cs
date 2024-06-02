using System;
using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.UI;

public class SimulationHandler : MonoBehaviour
{
    [SerializeField] Text _width;
    [SerializeField] Text _roadStartX;
    [SerializeField] Text _roadStartY;
    [SerializeField] Text _roadEndX;
    [SerializeField] Text _roadEndY;
    [SerializeField] Text _crossingWidth;
    [SerializeField] Text _crossingStartX;
    [SerializeField] Text _crossingStartY;
    [SerializeField] Text _crossingEndX;
    [SerializeField] Text _crossingEndY;
    
    [SerializeField] Text _agentsPositionX;
    [SerializeField] Text _agentsPositionY;
    [SerializeField] Text _agentsDestinationX;
    [SerializeField] Text _agentsDestinationY;
    [SerializeField] Text _agentsRadius;
    [SerializeField] Text _agentsDestinationRadius;
    [SerializeField] Text _agentsCount;
    
    static String id = null;

    public static SimulationHandler _instance;
    public static SimulationHandler Instance { get; private set; }
    

    void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
            DontDestroyOnLoad(gameObject);
        }
        else
        {
            Destroy(gameObject);
        }
    }
    
    public static Simulation simulation = new();
    public static List<Road> roads = new();
    public static List<AgentGroups> agentGroups = new();
    public static int simulationWidth = 200;
    public static int simulationHeight = 200;
    public static int scaleCoefficient = 1000;
    public static int destinationRadius = 3;
    public static int timeQuantum = 50;
    
    public static List<Agent> agents = new();
    
    
    public void addRoad()
    {
        string newWidth = _width.text;
        string newStartX = _roadStartX.text;
        string newStartY = _roadStartY.text;
        string newEndX = _roadEndX.text;
        string newEndY = _roadEndY.text;
        string newCrossingWidth = _crossingWidth.text;
        string newCrossingStartX = _crossingStartX.text;
        string newCrossingStartY = _crossingStartY.text;
        string newCrossingEndX = _crossingEndX.text;
        string newCrossingEndY = _crossingEndY.text;
        
        Road newRoad = new Road(newWidth, newStartX, newStartY, newEndX, newEndY, newCrossingWidth, newCrossingStartX, newCrossingStartY, newCrossingEndX, newCrossingEndY);

        //simulation.roads.Add(newRoad);
        roads.Add(newRoad);
        Debug.Log(JsonSerialization.ToJson(roads));
    }
    
    public void addAgentsGroup()
    {
        string newStartCenterX = _agentsPositionX.text;
        string newStartCenterY = _agentsPositionY.text;
        string newStartRadius = _agentsRadius.text;
        string newDestinationX = _agentsDestinationX.text;
        string newDestinationY = _agentsDestinationY.text;
        string destinationRadius = _agentsDestinationRadius.text;
        string agentsCount = _agentsCount.text;
        
        AgentGroups newAgentGroup = new AgentGroups(newStartCenterX, newStartCenterY, newStartRadius, newDestinationX, newDestinationY, destinationRadius, agentsCount);
        
        agentGroups.Add(newAgentGroup);
    }

    public static void setSimulation()
    {      
        simulation.set(roads, agentGroups, simulationWidth, simulationHeight,  scaleCoefficient, destinationRadius, timeQuantum);
    }
    
    public static Simulation getSimulation()
    {
        return simulation;
    }
    
    public static string getJson()
    {
        return(simulation.getJson());
    }
    
    public static void setBounds(int width, int height)
    {
        simulationWidth = width;
        simulationHeight = height;
    }
    
    public static String getId()
    {
        return id;
    }
    
    public static void setId(String newId)
    {
        id = newId;
    }
    
    public static void addAgent(float x, float y, float destinationX, float destinationY)
    {
        agents.Add(new Agent(x, y, destinationX, destinationY));
    }
    
    public static void setAgents(List<Agent> agentsList)
    {
        Debug.Log(agentsList.Count);
        agents = agentsList;
    }
}
