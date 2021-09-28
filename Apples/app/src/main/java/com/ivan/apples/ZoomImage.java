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
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ZoomImage extends androidx.appcompat.widget.AppCompatImageView {


    Paint _paint = new Paint(Color.BLACK);

    private Resources _res = null;
    private Bitmap _bmpSource = null;
    private Matrix _matrix = null;

    private Vector2 _firstFingerPosition = null;
    private Vector2 _secondFingerPosition = null;

    public void SetResources(Resources res){

        _res = res;
        _bmpSource = BitmapFactory.decodeResource(_res, R.drawable.resimg);

        _matrix = new Matrix();
        _matrix.setScale(0.7f, 0.7f);
    }


    public ZoomImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        _paint.setStrokeWidth(20f);
        //rect.setStrokeWidth(4);
        //rect.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);




        try{

            Bitmap bmp = Bitmap.createBitmap(_bmpSource, 0, 0, _bmpSource.getWidth(), _bmpSource.getHeight(), _matrix, true);

            canvas.drawBitmap(bmp, 0, 0, _paint);

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }


        //canvas.drawColor(Color.CYAN);
        //canvas.drawPoint(x, y, p);


        //canvas.drawRect(0, 0, 1500, 1500, rect);
    }

    public void SetFingers(Vector2 first, Vector2 second){

        _firstFingerPosition = first;
        _secondFingerPosition = second;

    }

    public void Zoom(Vector2 newFirst, Vector2 newSecond){

        try{

            float scalingFactor = (newSecond.subtract(newFirst)).magnitude() /
                    (_secondFingerPosition.subtract(_firstFingerPosition)).magnitude();

            Debug.Log("FACTOR: " + scalingFactor);



            _matrix.setScale(scalingFactor, scalingFactor);

            _firstFingerPosition = newFirst;
            _secondFingerPosition = newSecond;

            invalidate();

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }


    }


}
