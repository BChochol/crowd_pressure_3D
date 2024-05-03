using System.Collections;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.CompilerServices;
using UnityEngine;
using Newtonsoft.Json;

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
        
        Debug.Log(json);
        
        Dictionary<string, object> dict = JsonConvert.DeserializeObject<Dictionary<string, object>>(json);
        
        //foreach (KeyValuePair<string, object> pair in dict)
        //{
            Dictionary<string, object> responseDict = JsonConvert.DeserializeObject<Dictionary<string, object>>(dict["response"].ToString());
            Dictionary<string, object> bodyDict = JsonConvert.DeserializeObject<Dictionary<string, object>>(responseDict["body"].ToString());
            Dictionary<string, object> boardDict = JsonConvert.DeserializeObject<Dictionary<string, object>>(bodyDict["board"].ToString());
            

            simulation.simulationWidth = int.Parse(boardDict["width"].ToString());
            simulation.simulationHeight = int.Parse(boardDict["height"].ToString());
            
            List<object> wallsDict = JsonConvert.DeserializeObject<List<object>>(boardDict["walls"].ToString(), new JsonSerializerSettings{ObjectCreationHandling = ObjectCreationHandling.Replace });
            
            List<Dictionary<string, Dictionary<string, float>>> walls = new();

            foreach (object wall in wallsDict)
            {
                Dictionary<string, Dictionary<string, float>> newWall = new();
                
                Dictionary<string, object> wallDict =
                    JsonConvert.DeserializeObject<Dictionary<string, object>>(wall.ToString());
                
                Dictionary<string, float> start =
                    JsonConvert.DeserializeObject<Dictionary<string, float>>(wallDict["start"].ToString());
                Dictionary<string, float> end =
                    JsonConvert.DeserializeObject<Dictionary<string, float>>(wallDict["end"].ToString());
                
                newWall["start"] = start;
                newWall["end"] = end;
                walls.Append(newWall);
            }
        //}
        
        return simulation;
    }
}
