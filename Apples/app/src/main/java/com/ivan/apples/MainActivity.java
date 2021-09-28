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

        //TextView tv;
        //float x;
        //float y;
        String sDown = "";
        String sMove = "";
        String sUp = "";

        try{

            float x = event.getX();
            float y = event.getY();

            Vector2 fingerPosition = new Vector2( event.getX(),  event.getY());

            //prevFingerPos = null;

            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN: // нажатие
                    //sDown = "Down: " + x + "," + y;
                    //sMove = ""; sUp = "";



                    if(_resImageView){

                        prevFirstFingerPos = fingerPosition;

                        Debug.Log("ONE");
                    }
                    else {

                        _selectedGameObject = _scene.onTouchApple(fingerPosition);
                        Debug.Log("SET!");
                    }



                    break;

                case MotionEvent.ACTION_POINTER_DOWN:

                    if(_resImageView){

                        Debug.Log("TWO");
                        if(prevFirstFingerPos == null) Debug.Log("D_PREV!");
                        if(fingerPosition == null) Debug.Log("D_THIS!");

                        Debug.Log("ACTION INDEX: " + event.getPointerCount());

                        //_resImage.SetFingers(prevFirstFingerPos, fingerPosition);
                        _resImage.SetFingers(new Vector2(event.getX(0), event.getY(0)),
                                new Vector2(event.getX(1), event.getY(1)));


                        //prevSecondFingerPos = fingerPosition;
                    }

                    break;

                case MotionEvent.ACTION_MOVE: // движение
                    //sMove = "Move: " + x + "," + y;

                    if(_resImageView){

                        /*if(event.getPointerId(event.getActionIndex()) == 0){

                            _resImage.Zoom(fingerPosition, prevSecondFingerPos);
                        }
                        else {

                            //_resImage.Zoom(prevFirstFingerPos, fingerPosition);
                        }*/

                        //Debug.Log("ACTION INDEX: " + event.getPointerCount());

                        _resImage.Zoom(new Vector2(event.getX(0), event.getY(0)) ,
                                new Vector2(event.getX(1), event.getY(1)));

                        /*for(int i = 0; i<event.getPointerCount(); i++){


                        }


                        if(event.getPointerId(0) == 0){

                            prevSecondFingerPos = fingerPosition;

                        }
                        else{

                            Debug.Log("ACTION");
                            _resImage.Zoom(prevSecondFingerPos , fingerPosition);
                        }*/


                        if (prevFirstFingerPos == null) Debug.Log("M_PREV!");
                        if (fingerPosition == null) Debug.Log("M_THIS!");
                        //_resImage.Zoom(prevFirstFingerPos, fingerPosition);
                    }
                    else if(_selectedGameObject != null){

                        _scene.updatePosition(_selectedGameObject.id(), fingerPosition);
                    }



                    break;

                    //case MotionEvent.ACTION_POINTER_MOVE


                case MotionEvent.ACTION_UP: // отпускание
                case MotionEvent.ACTION_CANCEL:
                    //sMove = "";
                    //sUp = "Up: " + x + "," + y;

                    if(_selectedGameObject != null){

                        if(_scene.onReleaseApple(_selectedGameObject.id())){

                            _counter++;
                            _score.setText("Score: " + _counter);
                            Debug.Log("REMOVE!");

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

            Debug.Log(sDown + "\n" + sMove + "\n" + sUp);
        }
        catch (Exception ex){

            Debug.Log(ex.getMessage());
        }




        return true;
    }
}