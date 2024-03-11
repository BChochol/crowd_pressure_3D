using UnityEngine;
using UnityEngine.Networking;
using System.Collections;
using System.Collections.Generic;

public class MyBehavior : MonoBehaviour
{
    private Crossing _crossing = new();
    private Road _road = new();
    private Simulation _simulation = new();
    private byte[] byteArray;
    
    void Start()
    {
        _crossing.set(1.0f, 0.0f, 0.0f, 1.0f, 1.0f);
        _road.set(1.0f, 0.0f, 0.0f, 1.0f, 1.0f, _crossing);
        _simulation.set(new List<Road> { _road.get() }, 100, 100, 100, 100, 100, 100);
        string json = _simulation.getJson();
        byteArray = System.Text.Encoding.UTF8.GetBytes(_simulation.getJson());
        
        Debug.Log(_simulation.getJson());
        
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
            
            Debug.Log(request.GetResponseHeader("a"));
 
            if(request.result == UnityWebRequest.Result.ConnectionError || request.result == UnityWebRequest.Result.ProtocolError) {
 
                Debug.LogError(request.error);
            }
        }
    }
}