package com.ivan.apples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Scene2D _scene = null;

    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _scene = (Scene2D) findViewById(R.id.main_scene);
        _scene.SetResources(getResources());
        _scene.setOnTouchListener(this);


    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        //TextView tv;
        //float x;
        //float y;
        String sDown = "";
        String sMove = "";
        String sUp = "";


        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                sDown = "Down: " + x + "," + y;
                sMove = ""; sUp = "";
                break;
            case MotionEvent.ACTION_MOVE: // движение
                sMove = "Move: " + x + "," + y;
                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                sMove = "";
                sUp = "Up: " + x + "," + y;
                break;
        }

        List<Vector2> list = new ArrayList<Vector2>();
        list.add(new Vector2(x,y));

        _scene.setPoints(list);

        Debug.Log(sDown + "\n" + sMove + "\n" + sUp);

        return true;
    }
}