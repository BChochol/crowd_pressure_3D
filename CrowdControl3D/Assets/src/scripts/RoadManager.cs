using System;
using System.Collections;
using System.Collections.Generic;
using System.Globalization;
using UnityEngine;
using UnityEngine.Networking;

public class RoadManager : MonoBehaviour
{
    public GameObject roadPrefab;
    public GameObject agentPrefab;
    public GameObject crossing;

    private Dictionary<int, GameObject> agentObjects = new();
    private int agentCounter;
    private float updateInterval = 0.2f;
    private float lastUpdateTime = 0f;

    void Start()
    {
        Debug.Log("Starting RoadManager");
        Debug.Log(SimulationHandler.getJson());
        initializeRoads();
        initializeAgents();
    }

    void Update()
    {
        if (Time.time - lastUpdateTime >= updateInterval)
        {
            StartCoroutine(updateSimulation());
            lastUpdateTime = Time.time;
        }
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
            float length = Mathf.Sqrt(Mathf.Pow((start.x - end.x), 2) + Mathf.Pow((start.y - end.y), 2));

            newRoad.transform.rotation = Quaternion.Euler(0, 90 - Mathf.Atan2(end.y - start.y, end.x - start.x) * Mathf.Rad2Deg, 0);
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

            float x = ((startCrossingX * endCrossingY - startCrossingY * endCrossingX) * (startRoadX - endRoadX) - (startRoadX * endRoadY - startRoadY * endRoadX) * (startCrossingX - endCrossingX))
                / ((startCrossingX - endCrossingX) * (startRoadY - endRoadY) - (startCrossingY - endCrossingY) * (startRoadX - endRoadX));

            float y = ((startCrossingX * endCrossingY - startCrossingY * endCrossingX) * (startRoadY - endRoadY) - (startRoadX * endRoadY - startRoadY * endRoadX) * (startCrossingY - endCrossingY))
                / ((startCrossingX - endCrossingX) * (startRoadY - endRoadY) - (startCrossingY - endCrossingY) * (startRoadX - endRoadX));

            float angle = Mathf.Abs(Mathf.Atan((endRoadY * (x - endCrossingX) + y * (endRoadX - endCrossingX) +
                                    endCrossingY * (x - endRoadX)) / ((endRoadX - x) * (x - endCrossingX) + (endRoadY - y) * (y - endCrossingY))) * Mathf.Rad2Deg);

            MaterialPropertyBlock block = new MaterialPropertyBlock();
            newCrossing.GetComponent<MeshRenderer>().GetPropertyBlock(block);
            block.SetFloat("_Skew", 90 - angle);

            newCrossing.GetComponent<MeshRenderer>().SetPropertyBlock(block);

            newCrossing.transform.position = new Vector3(x, 0.002f, y);
            newCrossing.transform.localScale = new Vector3(float.Parse(road.width, CultureInfo.InvariantCulture.NumberFormat) / -10, 1f, float.Parse(road.crossing.width, CultureInfo.InvariantCulture.NumberFormat) / -10);
            newCrossing.transform.rotation = Quaternion.Euler(0.001f, 90 - Mathf.Atan2(end.y - start.y, end.x - start.x) * Mathf.Rad2Deg, 0);
        }
    }

    public void initializeAgents()
    {
        Debug.Log("Initializing agents. Count: " + SimulationHandler.agents.Count);
        agentCounter = 0;
        foreach (Agent agent in SimulationHandler.agents)
        {
            GameObject newAgent = Instantiate(agentPrefab, new Vector3(0, 0, 0), Quaternion.identity);
            newAgent.transform.position = new Vector3(agent.positionX, 0, agent.positionY);
            newAgent.name = "Agent_" + agentCounter;
            agentObjects[agentCounter] = newAgent;
            agentCounter++;
        }
    }

    IEnumerator updateSimulation()
    {
        using (UnityWebRequest request = new UnityWebRequest("http://localhost:8080/api/v1/simulation/" + SimulationHandler.getId() + "/step?steps=1"))
        {
            request.method = "PATCH";
            request.downloadHandler = new DownloadHandlerBuffer();
            request.SetRequestHeader("Content-Type", "application/json");
            yield return request.SendWebRequest();

            Debug.Log("Update simulation response code: " + request.responseCode);

            if (request.result == UnityWebRequest.Result.ConnectionError || request.result == UnityWebRequest.Result.ProtocolError)
            {
                Debug.LogError("Error in updateSimulation: " + request.error);
            }

            var StatusCode = request.responseCode;
            if (StatusCode == 200)
            {
                Debug.Log("Received agent updates: " + request.downloadHandler.text);
                List<Agent> agents = JsonSerialization.getAgentsList(request.downloadHandler.text);
                StartCoroutine(lerpAgents(agents));
            }
        }
    }

    IEnumerator lerpAgents(List<Agent> agents)
    {
        float lerpDuration = 0.2f; // Duration for position lerp
        float rotationLerpDuration = lerpDuration / 5; // Duration for rotation lerp

        float timeElapsed = 0;
        float rotationTimeElapsed = 0;

        Dictionary<int, Vector3> startPositions = new Dictionary<int, Vector3>();
        Dictionary<int, Vector3> endPositions = new Dictionary<int, Vector3>();
        Dictionary<int, Quaternion> startRotations = new Dictionary<int, Quaternion>();
        Dictionary<int, Quaternion> endRotations = new Dictionary<int, Quaternion>();

        for (int i = 0; i < agents.Count; i++)
        {
            if (agentObjects.ContainsKey(i))
            {
                GameObject agentObject = agentObjects[i];
                startPositions[i] = agentObject.transform.position;
                endPositions[i] = new Vector3(agents[i].positionX, 0, agents[i].positionY);

                Vector3 direction = endPositions[i] - startPositions[i];
                if (direction != Vector3.zero)
                {
                    Quaternion endRotation = Quaternion.LookRotation(direction);
                    startRotations[i] = agentObject.transform.rotation;
                    endRotations[i] = endRotation;
                }
            }
        }

        while (timeElapsed < lerpDuration)
        {
            timeElapsed += Time.deltaTime;
            rotationTimeElapsed += Time.deltaTime;
            float t = timeElapsed / lerpDuration;
            float rotationT = rotationTimeElapsed / rotationLerpDuration;

            for (int i = 0; i < agents.Count; i++)
            {
                if (agentObjects.ContainsKey(i))
                {
                    GameObject agentObject = agentObjects[i];
                    agentObject.transform.position = Vector3.Lerp(startPositions[i], endPositions[i], t);
                    if (startRotations.ContainsKey(i) && endRotations.ContainsKey(i))
                    {
                        agentObject.transform.rotation = Quaternion.Slerp(startRotations[i], endRotations[i], Mathf.Min(rotationT, 1f));
                    }

                    // Set animation state based on position
                    Transform dummyModel = agentObject.transform.Find("BasicMotionsDummyModel");
                    if (dummyModel != null)
                    {
                        Animator animator = dummyModel.GetComponent<Animator>();
                        if (animator != null)
                        {
                            bool isWalking = startPositions[i] != endPositions[i];
                            animator.SetBool("isWalking", isWalking);
                        }
                    }
                }
            }

            yield return null;
        }

        for (int i = 0; i < agents.Count; i++)
        {
            if (agentObjects.ContainsKey(i))
            {
                GameObject agentObject = agentObjects[i];
                agentObject.transform.position = new Vector3(agents[i].positionX, 0, agents[i].positionY);
                Vector3 direction = endPositions[i] - startPositions[i];
                if (direction != Vector3.zero)
                {
                    agentObject.transform.rotation = Quaternion.LookRotation(direction);
                }

                // Set final animation state
                Transform dummyModel = agentObject.transform.Find("BasicMotionsDummyModel");
                if (dummyModel != null)
                {
                    Animator animator = dummyModel.GetComponent<Animator>();
                    if (animator != null)
                    {
                        bool isWalking = startPositions[i] != endPositions[i];
                        animator.SetBool("isWalking", isWalking);
                    }
                }
            }
        }
    }


}
