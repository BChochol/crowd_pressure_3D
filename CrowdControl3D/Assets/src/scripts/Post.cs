using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using UnityEngine.SceneManagement;
using System.Collections.Generic;
using UnityEngineInternal;

public class PostHandler : MonoBehaviour
{
    private byte[] byteArray;
    
    public void sendPostRequest()
    {
        SimulationHandler.setSimulation();
        byteArray = System.Text.Encoding.UTF8.GetBytes(SimulationHandler.getJson());
        
        //Debug.Log(JsonSerialization.ToJson(_simulation.roads));
        Debug.Log(SimulationHandler.getJson());
        StartCoroutine(Upload());
    }

    IEnumerator Upload()
    {
        using (UnityWebRequest request = new UnityWebRequest("http://localhost:8080/api/v1/simulation"))
        {
            request.method = UnityWebRequest.kHttpVerbPOST;
            request.uploadHandler = new UploadHandlerRaw(byteArray);
            request.downloadHandler = new DownloadHandlerBuffer();
            request.SetRequestHeader("Content-Type", "application/json");
            yield return request.SendWebRequest();

            if(request.result == UnityWebRequest.Result.ConnectionError || request.result == UnityWebRequest.Result.ProtocolError) {
 
                Debug.LogError(request.error);
            }
            
            var StatusCode = request.responseCode;
            if (StatusCode == 200)
            {
                Debug.Log(request.downloadHandler.text);
                SimulationHandler.setId(JsonSerialization.getSimulationId(request.downloadHandler.text)); 
                List<Agent> agents = JsonSerialization.getAgentsList(request.downloadHandler.text);
                Debug.Log(agents.Count);
                SimulationHandler.setAgents(agents);
                //SimulationHandler.simulation = JsonSerialization.FromJson(request.downloadHandler.text);
                //Debug.Log(SimulationHandler.getJson());
                SceneManager.LoadScene("RoadScene");
            }
            
 

        }
    }
}