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
    //Paint rect = new Paint(Color.GREEN);
    float x = 0, y = 0;


    private Resources _res = null;
    private Bitmap _redApple = null;

    private Bitmap _redAppleSource = null;

    private Matrix _m = null;

    private List<Vector2> _points = null;




    public Scene2D(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _paint.setStrokeWidth(40f);
        _paint.setColor(Color.RED);

        _m = new Matrix();
        _m.setScale(0.25f, 0.25f);

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

            //canvas.drawRect(0, 0, 1500, 1500, rect);

            if(_res == null || _points == null) return;

            for(Vector2 point : _points){

                //canvas.drawColor(Color.YELLOW);
                //canvas.drawPoint(point.getX(), point.getY(), _paint);

                //Matrix matrix = new Matrix();
                //matrix.setScale(0.95f, 0.95f);

                _redApple = Bitmap.createBitmap(_redAppleSource, 0, 0,
                        _redAppleSource.getWidth(), _redAppleSource.getHeight(), _m, true);

                //canvas.drawBitmap(_redApple, _m, _paint);

                canvas.drawBitmap(_redApple, point.getX() - _redApple.getWidth()/2, point.getY() -_redApple.getHeight()/2, _paint);

                //canvas.drawBitmap(_redApple, (int)point.getX(), point.getY(), new Matrix().setScale(0.5f, 0.5f), _paint);

            }

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }



    }


    public void setPoints(List<Vector2> points){

        _points = points;
        invalidate();
    }


}
