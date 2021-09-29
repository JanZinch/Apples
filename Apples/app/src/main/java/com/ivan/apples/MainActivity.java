package com.ivan.apples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private Scene2D _scene = null;
    private GameObject _selectedGameObject = null;

    private TextView _score = null;
    private int _counter = 0;
    private FloatingActionButton _restartBtn = null;

    private ZoomImage _resImage = null;
    private boolean _resImageView = false;
    private Vector2 prevFirstFingerPos = null;
    private Vector2 prevSecondFingerPos = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _scene = (Scene2D) findViewById(R.id.main_scene);
        _scene.SetResources(getResources());
        _scene.setOnTouchListener(this);

        _score = (TextView) findViewById(R.id.score);
        _restartBtn = (FloatingActionButton) findViewById(R.id.restart_btn);

        _resImage = (ZoomImage) findViewById(R.id.res_img);
        _resImage.SetResources(getResources());
    }

    @Override
    protected void onStart() {
        super.onStart();

        _scene.InitGameObjects();

        _resImage.setVisibility(View.INVISIBLE);

        _restartBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                _counter = 0;
                _score.setText("Score: 0");
                _selectedGameObject = null;
                _scene.InitGameObjects();
                _resImage.setVisibility(View.INVISIBLE);
                _resImageView = false;

            }
        });

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        try{

            Vector2 fingerPosition = new Vector2( event.getX(),  event.getY());

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN: // нажатие

                    if(_resImageView){

                        prevFirstFingerPos = fingerPosition;
                    }
                    else {

                        _selectedGameObject = _scene.onTouchApple(fingerPosition);
                        //Debug.Log("SET!");
                    }

                    break;

                case MotionEvent.ACTION_POINTER_DOWN:

                    if(_resImageView){

                        //Debug.Log("ACTION INDEX: " + event.getPointerCount());

                        _resImage.SetFingers(new Vector2(event.getX(0), event.getY(0)),
                                new Vector2(event.getX(1), event.getY(1)));

                    }

                    break;

                case MotionEvent.ACTION_MOVE: // движение

                    if(_resImageView){

                        _resImage.Zoom(new Vector2(event.getX(0), event.getY(0)) ,
                                new Vector2(event.getX(1), event.getY(1)));

                    }
                    else if(_selectedGameObject != null){

                        _scene.updatePosition(_selectedGameObject.id(), fingerPosition);
                    }

                    break;


                case MotionEvent.ACTION_UP: // отпускание
                case MotionEvent.ACTION_CANCEL:

                    if(_selectedGameObject != null){

                        if(_scene.onReleaseApple(_selectedGameObject.id())){

                            _counter++;
                            _score.setText("Score: " + _counter);

                            if(_scene.getApplesCount() == 0){

                                _resImage.setVisibility(View.VISIBLE);
                                _resImageView = true;
                            }

                        }
                        _selectedGameObject = null;
                    }

                    break;

                    case MotionEvent.ACTION_POINTER_UP:

                        if(_resImageView){

                            _resImage.SetFingers(null, null);
                        }

                        break;

            }

        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }




        return true;
    }
}