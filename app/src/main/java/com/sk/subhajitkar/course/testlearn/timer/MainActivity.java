package com.sk.subhajitkar.course.testlearn.timer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private SeekBar seekBar;
    private Button button;
    private ImageView egg, timerLogo;
    private TextView timer;
    public int seconds = 10,currentSecond=0;
    String startTime = "00:00:00";
    public CountDownTimer countDownTimer;
    MediaPlayer mediaPlayer1, mediaPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //widgets initialization
        timer = findViewById(R.id.tv_timer);
        egg = findViewById(R.id.iv_egg);
        timerLogo = findViewById(R.id.iv_timer);
        button = findViewById(R.id.button);
        seekBar = findViewById(R.id.seekBar);

        button.setText("Start");
        timer.setText("00:00:10");

        setupSeekBar();
        mediaPlayer1 = MediaPlayer.create(this,R.raw.tick);
        mediaPlayer2 = MediaPlayer.create(this,R.raw.alarm);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button.getText().equals("Start")) {
                    button.setText("Stop");
                    if (currentSecond<=0){
                        currentSecond = seconds;
                    }
                    setupCountDownTimer();
                    countDownTimer.start();
                } else if (button.getText().toString().equals("Stop")) {
                    button.setText("Start");
                    countDownTimer.cancel();
                }
            }
        });
    }

    private void setupSeekBar() {
        //seekBar
        seekBar.setMax(3600);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int min = 10;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (button.getText().toString().equals("Stop")){
                    countDownTimer.cancel();
                }
                currentSecond=0;
                button.setText("Start");
                if (progress > min) {
                    seconds = progress;
                } else {
                    seconds = min;
                }
                String newtime = String.format("%02d:%02d:%02d",(seconds/3600),
                        ((seconds%3600)/60),
                        (seconds%60));
                timer.setText(newtime);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void setupCountDownTimer() {

        countDownTimer = new CountDownTimer((currentSecond * 1000), 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                //in each interval count
                currentSecond--;
                Log.d(TAG, "onTick: "+millisUntilFinished);
                String newtime = String.format("%02d:%02d:%02d",((millisUntilFinished/1000)/3600),
                        (((millisUntilFinished/1000)%3600)/60),
                        ((millisUntilFinished/1000)%60));
                timer.setText(newtime);
                mediaPlayer1.start();
            }

            @Override
            public void onFinish() {
                //after the total time
                timer.setText(startTime);
                currentSecond=0;
                button.setText("Start");
                mediaPlayer2.start();
            }
        };
    }
}
