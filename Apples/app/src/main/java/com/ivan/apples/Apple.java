package com.ivan.apples;

public class Apple {

    private int _id;
    private Color _color;
    private Vector2 _position;


    public Apple(int id, Vector2 position, Color color){

        _id = id;
        _position = position;
        _color = color;

    }

    public enum Color{

        RED, GREEN
    }

    public void setPosition(Vector2 position){

        _position = position;
    }

    public Vector2 getPosition(){

        return _position;
    }

    public int id(){

        return _id;
    }

}

