package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView timerText;
    Button toggleButton;
    SeekBar seekBar;
    CountDownTimer countDownTimer;
    long timerLimitLong;
    boolean isTimerInProgress = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seekBar = findViewById(R.id.seekBar);
        timerText = findViewById(R.id.timerText);
        toggleButton = findViewById(R.id.toggleButton);
        updateButtonAndSeekbar();

        seekBar.setMax(300);
        seekBar.setMin(30);
        seekBar.setProgress(150);
        timerText.setText("0:30");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timerLimitLong = Long.valueOf(i);
                String zero = "0";
                String timeInMins = String.valueOf(i/60);
                int timeInSecondsInt = i%60;
                String timeInSeconds = String.valueOf(i%60);
                if(timeInSecondsInt<10) {
                    timeInSeconds = zero + timeInSeconds;
                }
                timerText.setText(timeInMins + ":" + timeInSeconds);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void toggleTimer(View view) {
        if (isTimerInProgress) {
            countDownTimer.cancel();
            isTimerInProgress = false;
            updateButtonAndSeekbar();
            seekBar.setProgress(150);
            timerText.setText("2:50");
        } else {

            countDownTimer = new CountDownTimer(timerLimitLong *1000, 1000) {

                @Override
                public void onTick(long milliseconds) {
                    Log.i("Time left", String.valueOf(milliseconds/1000));
                    isTimerInProgress = true;
                    updateButtonAndSeekbar();

                    String zero = "0";
                    String timeLeftInMinutes = String.valueOf((milliseconds/1000)/60);
                    long timeLeftInSecondsLong = (milliseconds/1000)%60;
                    String timeLeftInSeconds = String.valueOf((milliseconds/1000)%60);
                    if(timeLeftInSecondsLong<10) {
                        timeLeftInSeconds = zero + timeLeftInSeconds;
                    }

                    timerText.setText(timeLeftInMinutes + ":" + timeLeftInSeconds);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell);
                    mediaPlayer.start();
                    isTimerInProgress = false;
                    updateButtonAndSeekbar();
                }

            }.start();
        }
    }


    public void updateButtonAndSeekbar() {
        if(isTimerInProgress) {
            toggleButton.setText("Stop");
            seekBar.setEnabled(false);
        } else {
            toggleButton.setText("Go");
            seekBar.setEnabled(true);
        }
    }
}
