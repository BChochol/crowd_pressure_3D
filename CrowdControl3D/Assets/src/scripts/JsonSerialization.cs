using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class JsonSerialization : MonoBehaviour
{
    public static string ToJson(Dictionary<string, float> dictionary)
    {
        string json = "{";
        foreach (KeyValuePair<string, float> pair in dictionary)
        {
            json += "\"" + pair.Key + "\":" + pair.Value + ",";
        }
        json = json.Remove(json.Length - 1);
        json += "}";
        return json;
    }
    
    public static string ToJson(Dictionary<string, string> dictionary)
    {
        string json = "{";
        foreach (KeyValuePair<string, string> pair in dictionary)
        {
            json += "\"" + pair.Key + "\":" + pair.Value + ",";
        }
        json = json.Remove(json.Length - 1);
        json += "}";
        return json;
    }
    
    public static string ToJson(Crossing crossing)
    {
        string json = "{";
        json += "\"width\":" + crossing.width + ",";
        json += "\"start\":" + ToJson(crossing.start) + ",";
        json += "\"end\":" + ToJson(crossing.end);
        json += "}";
        return json;
    }
    
    public static string ToJson(Road road)
    {
        string json = "{";
        json += "\"width\":" + road.width + ",";
        json += "\"start\":" + ToJson(road.start) + ",";
        json += "\"end\":" + ToJson(road.end) + ",";
        json += "\"crossing\":" + ToJson(road.crossing);
        json += "}";
        return json;
    }
    
    public static string ToJson(AgentGroups agentGroup)
    {
        string json = "{";
        json += "\"startCenter\":" + ToJson(agentGroup.startCenter) + ",";
        json += "\"startRadius\":" + agentGroup.startRadius + ",";
        json += "\"destination\":" + ToJson(agentGroup.destination) + ",";
        json += "\"destinationRadius\":" + agentGroup.destinationRadius + ",";
        json += "\"groupSize\":" + agentGroup.groupSize;
        json += "}";
        return json;
    }
    
    public static string ToJson(List<Road> roads)
    {
        string json = "[";
        foreach (Road road in roads)
        {
            json += ToJson(road) + ",";
        }
        json = json.Remove(json.Length - 1);
        json += "]";
        return json;
    }
    
    public static string ToJson(List<AgentGroups> agentGroups)
    {
        string json = "[";
        foreach (AgentGroups agentGroup in agentGroups)
        {
            json += ToJson(agentGroup) + ",";
        }
        json = json.Remove(json.Length - 1);
        json += "]";
        return json;
    }

    public static string ToJson(Simulation simulation)
    {
        string json = "{";
        json += "\"roads\":" + ToJson(simulation.roads) + ",";
        json += "\"agentGroups\":" + ToJson(simulation.agentGroups) + ",";
        json += "\"simulationWidth\":" + simulation.simulationWidth + ",";
        json += "\"simulationHeight\":" + simulation.simulationHeight + ",";
        json += "\"scaleCoefficient\":" + simulation.scaleCoefficient + ",";
        json += "\"destinationRadius\":" + simulation.destinationRadius + ",";
        json += "\"timeQuantum\":" + simulation.timeQuantum;
        json += "}";
        return json;
    }
    
    public static Simulation FromJson(string json)
    {
        Simulation simulation = new();
        
        Dictionary<string, object> dict = JsonUtility.FromJson<Dictionary<string, object>>(json);
        return simulation;
    }
}
