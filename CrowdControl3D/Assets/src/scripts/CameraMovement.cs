using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraMovement : MonoBehaviour
{
    
    [SerializeField] private static InputActions inputActions;

    private void Awake()
    {
        inputActions = new InputActions();
        inputActions.Enable();
    }

    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        Vector2 moveVector = inputActions.Keyboard.Move.ReadValue<Vector2>().normalized;
        float zoom = inputActions.Keyboard.Zoom.ReadValue<float>();
        transform.RotateAround(Vector3.zero, Vector3.up, -moveVector.x);
        transform.LookAt(Vector3.zero);
        if (transform.rotation.x < 90)
        {
        }
        transform.RotateAround(Vector3.zero, transform.right, moveVector.y);

        transform.position += transform.forward * zoom * 0.01f;
    }
}
