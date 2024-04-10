using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using System.Collections.Generic;

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
        using (UnityWebRequest request = new UnityWebRequest("http://localhost:8080/api/v1/simulation/"))
        {
            request.method = UnityWebRequest.kHttpVerbPOST;
            request.uploadHandler = new UploadHandlerRaw(byteArray);
            request.downloadHandler = new DownloadHandlerBuffer();
            request.SetRequestHeader("Content-Type", "application/json");
            yield return request.SendWebRequest();


            var StatusCode = request.responseCode;
 
            Debug.Log("Return code: " + StatusCode);
            
            Debug.Log(request.downloadHandler.text);
 
            if(request.result == UnityWebRequest.Result.ConnectionError || request.result == UnityWebRequest.Result.ProtocolError) {
 
                Debug.LogError(request.error);
            }
        }
    }
}