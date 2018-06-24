package com.bobbysandhu.profootball;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final long START_TIME_IN_MILLIS = 90000; // have set 1.5 min

    private int mScoreHost = 0;
    private int mScoreGuest = 0;
    private TextView mStopWatch;
    private TextView mTextScoreHost;
    private TextView mTextScoreGuest;
    private Button mButtonTouchDownHost;
    private Button mButtonTouchDownGuest;
    private Button mButtonfieldGoalHost;
    private Button mButtonfieldGoalGuest;
    private Button mButtonSafetyHost;
    private Button mButtonSafetyGuest;
    private Button mButtonExtraHostOnePoint;
    private Button mButtonExtraHostTwoPoint;
    private Button mButtonExtraGuestOnePoint;
    private Button mButtonExtraGuestTwoPoint;
    private ImageView imageReset;
    private ImageView imagePlay;
    private boolean isPlaying;
    private CountDownTimer mCountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mStopWatch = findViewById(R.id.stopwatch);
        mTextScoreHost = findViewById(R.id.text_score_host);
        mTextScoreGuest = findViewById(R.id.text_score_guest);
        mButtonTouchDownHost = findViewById(R.id.button_host_touchdown);
        mButtonTouchDownGuest = findViewById(R.id.button_guest_touchdown);
        mButtonfieldGoalHost = findViewById(R.id.button_host_field_goal);
        mButtonfieldGoalGuest = findViewById(R.id.button_guest_field_goal);
        mButtonSafetyHost = findViewById(R.id.button_host_safety);
        mButtonSafetyGuest = findViewById(R.id.button_guest_safety);
        mButtonExtraHostOnePoint = findViewById(R.id.button_host_extra_one);
        mButtonExtraHostTwoPoint = findViewById(R.id.button_host_extra_two);
        mButtonExtraGuestOnePoint = findViewById(R.id.button_guest_extra_one);
        mButtonExtraGuestTwoPoint = findViewById(R.id.button_guest_extra_two);
        imageReset = findViewById(R.id.image_reset);
        imagePlay = findViewById(R.id.image_play);

        // disabled button - click play to start the game and timer
        enableDisableButtons(false);
    }

    private void updateScore(int hostScore, int guestScore) {
        mTextScoreHost.setText(String.valueOf(hostScore));
        mTextScoreGuest.setText(String.valueOf(guestScore));
    }

    public void touchDownHost(View view) {
        mScoreHost = mScoreHost + 6;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void touchDownGuest(View view) {
        mScoreGuest = mScoreGuest + 6;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void fieldGoalHost(View view) {
        mScoreHost = mScoreHost + 3;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void fieldGoalGuest(View view) {
        mScoreGuest = mScoreGuest + 3;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void safetyHost(View view) {
        mScoreHost = mScoreHost + 2;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void safetyGuest(View view) {
        mScoreGuest = mScoreGuest + 2;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void extraOnePointHost(View view) {
        mScoreHost = mScoreHost + 1;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void extraTwoPointHost(View view) {
        mScoreHost = mScoreHost + 2;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void extraOnePointGuest(View view) {
        mScoreGuest = mScoreGuest + 1;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void extraTwoPointGuest(View view) {
        mScoreGuest = mScoreGuest + 2;
        updateScore(mScoreHost, mScoreGuest);
    }

    public void play(View view) {
        if (!isPlaying) {
            enableDisableButtons(true);
            startTimer();
        }
    }

    public void reset(View view) {
        mScoreHost = 0;
        mScoreGuest = 0;
        updateScore(mScoreHost, mScoreGuest);
        enableDisableButtons(false);
        mTimeLeftInMillis = START_TIME_IN_MILLIS;
        pauseTimer();
        updateCountDownText();
    }

    private void enableDisableButtons(boolean status) {
        mButtonTouchDownHost.setEnabled(status);
        mButtonTouchDownGuest.setEnabled(status);
        mButtonfieldGoalHost.setEnabled(status);
        mButtonfieldGoalGuest.setEnabled(status);
        mButtonSafetyHost.setEnabled(status);
        mButtonSafetyGuest.setEnabled(status);
        mButtonExtraHostOnePoint.setEnabled(status);
        mButtonExtraHostTwoPoint.setEnabled(status);
        mButtonExtraGuestOnePoint.setEnabled(status);
        mButtonExtraGuestTwoPoint.setEnabled(status);
    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                enableDisableButtons(false);

                int scoreHost = Integer.valueOf(mTextScoreHost.getText().toString());
                int scoreGuest = Integer.valueOf(mTextScoreGuest.getText().toString());

                if (scoreHost == scoreGuest) {
                    Toast.makeText(MainActivity.this, "Match Draw", Toast.LENGTH_LONG).show();
                    return;
                }

                if (scoreHost > scoreGuest)
                    Toast.makeText(MainActivity.this, "Hosts wins this game with " + scoreHost + " points.", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(MainActivity.this, "Guests wins this game with " + scoreGuest + " points.", Toast.LENGTH_LONG).show();

            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        mStopWatch.setText(timeLeftFormatted);
    }

    private void pauseTimer() {
        mCountDownTimer.cancel();
        mTimerRunning = false;
    }
}
