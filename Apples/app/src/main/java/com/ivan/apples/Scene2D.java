package com.ivan.apples;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Scene2D extends androidx.appcompat.widget.AppCompatImageView {

    Paint _paint = new Paint(Color.BLACK);

    float x = 0, y = 0;

    public List<Apple> apples = null;


    private Resources _res = null;
    private Bitmap _redApple = null;
    private Bitmap _redAppleSource = null;
    private Matrix _matrix = null;


    private final float _appleRadius = 150.0f;




    public Scene2D(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _paint.setStrokeWidth(40f);
        _paint.setColor(Color.RED);

        _matrix = new Matrix();
        _matrix.setScale(0.1f, 0.1f);

        //if(_res != null) Debug.Log("RES");
        //if



        /*rect.setStrokeWidth(4);
        rect.setStyle(Paint.Style.STROKE);*/
    }

    public void SetResources(Resources res){

        _res = res;
        _redAppleSource = BitmapFactory.decodeResource(_res, R.drawable.red_apple);



    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.YELLOW);

        try{

            if(_res == null) return;

            for(Apple apple : apples){

                Debug.Log("DRAW!");

                _redApple = Bitmap.createBitmap(_redAppleSource, 0, 0, _redAppleSource.getWidth(), _redAppleSource.getHeight(), _matrix, true);
                canvas.drawBitmap(_redApple, apple.getPosition().getX() - _redApple.getWidth()/2, apple.getPosition().getY() -_redApple.getHeight()/2, _paint);
            }

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }



    }

    public Apple onTouchApple(Vector2 point){

        for(Apple apple : apples){

            if( point.getX() > apple.getPosition().getX() - _appleRadius && point.getX() < apple.getPosition().getX() + _appleRadius
                    && point.getY() > apple.getPosition().getY() - _appleRadius && point.getY() < apple.getPosition().getY()){

                Debug.Log("TARGET!");
                return apple;
            }
        }

        return null;
    }

    public void updatePosition(int id, Vector2 newPosition){

        for(Apple apple : apples){

            if(id == apple.id()){

                Debug.Log("OLD: " + newPosition.getX() + "   NEW: " + apple.getPosition().getX());

                apple.setPosition(newPosition);
                Debug.Log("UPDATED!");
            }
        }

        invalidate();
    }



}
