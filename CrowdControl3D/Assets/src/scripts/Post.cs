using UnityEngine;
using UnityEngine.Networking;
using System.Collections;

public class MyBehavior : MonoBehaviour
{
    private string jsonData =
        "{\"roads\":[ {         \"start\":{ \"x\":1.0, \"y\":1.0 }, \"end\":{ \"x\":2.0, \"y\":2.0 }, \"width\":2.3, \"crossing\":{ \"start\":{ \"x\":1.0,  \"y\":1.0 }, \"end\":{ \"x\":2.0,  \"y\":2.0          },           \"width\":2.3        }     }  ],  \"simulationWidth\":100, \"simulationHeight\":100, \"agentCount\":100, \"scaleCoefficient\":10.0, \"destinationRadius\":10.0, \"timeQuantum\":10.0}";
    
    void Start()
    {
        StartCoroutine(Upload());
    }

    IEnumerator Upload()
    {
        using (UnityWebRequest request = UnityWebRequest.PostWwwForm("http://localhost:8080/api/v1/simulation/1", jsonData))
        {
            yield return request.SendWebRequest();

            var StatusCode = request.responseCode;
 
            Debug.Log("Return code: " + StatusCode);
 
            if(request.isNetworkError || request.isHttpError) {
 
                Debug.LogError(request.error);
            }
        }
    }
}