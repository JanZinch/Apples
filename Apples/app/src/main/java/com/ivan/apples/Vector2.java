package com.ivan.apples;

public class Vector2 {


    private float x, y;

    Vector2() {
        x = y = 0.0f;
    }

    Vector2(float x, float y) {

        this.x = x;
        this.y = y;
    }

    public Vector2 multiply(float f) {

        return new Vector2(x * f, y * f);
    }


    Vector2 add(Vector2 v) {
        return new Vector2(x + v.x, y + v.y);
    }

    Vector2 subtract(float f) {
        return new Vector2(x - f, y - f);
    }

    Vector2 subtract(Vector2 v) {
        return new Vector2(x - v.x, y - v.y);
    }



    float getX(){

        return x;
    }

    float getY(){

        return y;
    }


}
