package com.ivan.apples;

public class GameObject {

    private int _id;
    private Color _color;
    private Vector2 _position;


    public GameObject(int id, Vector2 position, Color color){

        _id = id;
        _position = position;
        _color = color;

    }

    public enum Color {

        RED_APPLE, GREEN_APPLE, BASKET
    }

    public void setPosition(Vector2 position){

        _position = position;
    }

    public Color getColor(){

        return _color;
    }

    public Vector2 getPosition(){

        return _position;
    }

    public int id(){

        return _id;
    }

}

