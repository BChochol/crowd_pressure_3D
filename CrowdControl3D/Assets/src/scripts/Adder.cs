using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UIElements;

public class Adder : MonoBehaviour
{
    // Start is called before the first frame update

     void Start()
    {
        VisualElement root = GetComponent<UIDocument>().rootVisualElement;
        
        //get text from all the input fields to different varibles
        String width = root.Q<TextField>("width").text;
        String roadStartX = root.Q<TextField>("roadStartX").text;
        String roadStartY = root.Q<TextField>("roadStartY").text;
        String roadEndX = root.Q<TextField>("roadEndX").text;
        String roadEndY = root.Q<TextField>("roadEndY").text;
        String crossingWidth = root.Q<TextField>("crossingWidth").text;
        String crossingStartX = root.Q<TextField>("crossingStartX").text;
        String crossingStartY = root.Q<TextField>("crossingStartY").text;
        String crossingEndX = root.Q<TextField>("crossingEndX").text;
        String crossingEndY = root.Q<TextField>("crossingEndY").text;
        
        Button button = root.Q<Button>("addRoad");
        //button.clicked += () => { SimulationHandler.Instance.addRoad(width, roadStartX, roadStartY, roadEndX, roadEndY, crossingWidth, crossingStartX, crossingStartY, crossingEndX, crossingEndY); };
        
        Button button2 = root.Q<Button>("addAgentGroup");
        button2.clicked += () => { SimulationHandler.Instance.addAgentsGroup(); };
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }
}
