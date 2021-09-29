package com.ivan.apples;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Scene2D extends androidx.appcompat.widget.AppCompatImageView {

    Paint _paint = new Paint(android.graphics.Color.BLACK);

    float x = 0, y = 0;

    public List<GameObject> gameObjects = null;

    private Resources _res = null;
    private Bitmap _gameObjectBmp = null;
    private Bitmap _redAppleSource = null;
    private Bitmap _greenAppleSource = null;
    private Bitmap _basketSource = null;

    private Matrix _appleMatrix = null;

    private Matrix _basketMatrix = null;

    private final float _appleRadius = 100.0f;
    private final float _basketRadius = 150.0f;


    private Vector2 _basketPos = null;


    public Scene2D(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _paint.setStrokeWidth(40f);
        _paint.setColor(android.graphics.Color.RED);

        _appleMatrix = new Matrix();
        _appleMatrix.setScale(0.1f, 0.1f);

        _basketMatrix = new Matrix();
        _basketMatrix.setScale(0.2f, 0.2f);

    }

    public void SetResources(Resources res){

        _res = res;
        _redAppleSource = BitmapFactory.decodeResource(_res, R.drawable.red_apple);
        _greenAppleSource = BitmapFactory.decodeResource(_res, R.drawable.green_apple);
        _basketSource = BitmapFactory.decodeResource(_res, R.drawable.basket);
    }

    public void InitGameObjects(){

        gameObjects = new ArrayList<GameObject>();

        if(_basketPos != null){

            gameObjects.add(new GameObject(0, _basketPos, GameObject.Color.BASKET));
        }
        else{
            gameObjects.add(new GameObject(0, new Vector2(0, 0), GameObject.Color.BASKET));
        }

        gameObjects.add(new GameObject(1, new Vector2(300.0f, 500.0f), GameObject.Color.RED_APPLE));
        gameObjects.add(new GameObject(2, new Vector2(700.0f, 300.0f), GameObject.Color.GREEN_APPLE));
        gameObjects.add(new GameObject(3, new Vector2(300.0f, 1200.0f), GameObject.Color.GREEN_APPLE));
        gameObjects.add(new GameObject(4, new Vector2(700.0f, 1600.0f), GameObject.Color.RED_APPLE));
        gameObjects.add(new GameObject(5, new Vector2(500.0f, 300.0f), GameObject.Color.RED_APPLE));

        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if(_basketPos == null) _basketPos = new Vector2(getWidth()/2, getHeight()/2);
        gameObjects.get(0).setPosition(_basketPos);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(android.graphics.Color.YELLOW);



        try{

            if(_res == null) return;

            for(GameObject gameObject : gameObjects){

                if(gameObject.getColor() == GameObject.Color.RED_APPLE){

                    _gameObjectBmp = Bitmap.createBitmap(_redAppleSource, 0, 0, _redAppleSource.getWidth(), _redAppleSource.getHeight(), _appleMatrix, true);


                }
                else if(gameObject.getColor() == GameObject.Color.GREEN_APPLE){

                    _gameObjectBmp = Bitmap.createBitmap(_greenAppleSource, 0, 0, _greenAppleSource.getWidth(), _greenAppleSource.getHeight(), _appleMatrix, true);

                }
                else{

                    _gameObjectBmp = Bitmap.createBitmap(_basketSource, 0, 0, _basketSource.getWidth(), _basketSource.getHeight(), _basketMatrix, true);
                }

                canvas.drawBitmap(_gameObjectBmp, gameObject.getPosition().getX() - _gameObjectBmp.getWidth()/2, gameObject.getPosition().getY() - _gameObjectBmp.getHeight()/2, _paint);

            }

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }



    }


    private boolean checkRadiusConstraint(Vector2 point, Vector2 objectPosition, float radius){

        return point.getX() > objectPosition.getX() - radius && point.getX() <objectPosition.getX() + radius
                && point.getY() > objectPosition.getY() - radius && point.getY() < objectPosition.getY() + radius;
    }

    public GameObject onTouchApple(Vector2 point){

        for(GameObject gameObject : gameObjects){

            if( gameObject.getColor() != GameObject.Color.BASKET && checkRadiusConstraint(point, gameObject.getPosition(), _appleRadius)){

                //Debug.Log("TARGET!");
                invalidate();
                return gameObject;
            }
        }

        return null;
    }

    public boolean onReleaseApple(int id){

        try{

            int p = -1;

            for(GameObject gameObject : gameObjects){

                if(id == gameObject.id() && checkRadiusConstraint(gameObject.getPosition(), gameObjects.get(0).getPosition(), _basketRadius)){

                    p = gameObjects.indexOf(gameObject);
                    break;
                }

            }

            if (p != -1){

                gameObjects.remove(p);

                invalidate();
                return true;
            }
            else{

                return false;
            }


        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }



        return false;



    }

    public int getApplesCount(){

        return gameObjects.size() - 1;
    }


    public void updatePosition(int id, Vector2 newPosition){

        for(GameObject gameObject : gameObjects){

            if(id == gameObject.id()){

                //Debug.Log("OLD: " + newPosition.getX() + "   NEW: " + gameObject.getPosition().getX());

                gameObject.setPosition(newPosition);
                //Debug.Log("UPDATED!");
            }
        }

        invalidate();
    }



}
