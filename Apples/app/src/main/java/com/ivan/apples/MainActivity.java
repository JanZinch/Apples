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

    private Apple _selectedApple = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _scene = (Scene2D) findViewById(R.id.main_scene);
        _scene.SetResources(getResources());
        _scene.setOnTouchListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();

        _scene.apples = new ArrayList<Apple>();
        _scene.apples.add(new Apple(0, new Vector2(45.0f, 190.0f), Apple.Color.RED));
        _scene.apples.add(new Apple(1, new Vector2(300.0f, 300.0f), Apple.Color.GREEN));
        _scene.apples.add(new Apple(2, new Vector2(145.0f, 190.0f), Apple.Color.GREEN));

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

        Vector2 fingerPosition = new Vector2( event.getX(),  event.getY());


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // нажатие
                sDown = "Down: " + x + "," + y;
                sMove = ""; sUp = "";

                _selectedApple = _scene.onTouchApple(fingerPosition);


                break;
            case MotionEvent.ACTION_MOVE: // движение
                sMove = "Move: " + x + "," + y;

                if(_selectedApple != null){

                    _scene.updatePosition(_selectedApple.id(), fingerPosition);
                }

                break;
            case MotionEvent.ACTION_UP: // отпускание
            case MotionEvent.ACTION_CANCEL:
                sMove = "";
                sUp = "Up: " + x + "," + y;

                _selectedApple = null;

                break;
        }



        Debug.Log(sDown + "\n" + sMove + "\n" + sUp);

        return true;
    }
}