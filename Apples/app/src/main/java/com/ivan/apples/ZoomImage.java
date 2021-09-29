package com.ivan.apples;

import android.content.Context;
import android.content.res.Configuration;
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


    private Paint _paint = new Paint(Color.BLACK);

    private Resources _res = null;
    private Bitmap _bmpSource = null;
    private Matrix _matrix = null;

    private Vector2 _originalScale = new Vector2(0.25f, 0.25f);

    private Vector2 _firstFingerPosition = null;
    private Vector2 _secondFingerPosition = null;

    public void SetResources(Resources res){

        _res = res;
        _bmpSource = BitmapFactory.decodeResource(_res, R.drawable.resimg);

        if ((_res.getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE){

            _originalScale = new Vector2(0.7f, 0.7f);
        }

        _matrix = new Matrix();
        _matrix.setScale(_originalScale.getX(), _originalScale.getY());

    }


    public ZoomImage(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        _paint.setStrokeWidth(20f);
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
    }

    public void SetFingers(Vector2 first, Vector2 second){

        _firstFingerPosition = first;
        _secondFingerPosition = second;

    }

    public void Zoom(Vector2 newFirst, Vector2 newSecond){

        try{

            float scalingFactor = (newSecond.subtract(newFirst)).magnitude() /
                    (_secondFingerPosition.subtract(_firstFingerPosition)).magnitude();


            _matrix.setScale(_originalScale.getX() * scalingFactor, _originalScale.getY() * scalingFactor);

            invalidate();

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }


    }


}
