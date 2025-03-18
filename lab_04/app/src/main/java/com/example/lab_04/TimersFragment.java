package com.example.lab_04;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimersFragment extends Fragment {

    // Minutnik 1
    private EditText etTime1;
    private TextView tvTimer1;
    private Button btnStart1, btnPause1, btnReset1;
    private CountDownTimer countDownTimer1;
    private boolean isRunning1 = false;
    private long timeLeft1 = 0;

    // Minutnik 2
    private EditText etTime2;
    private TextView tvTimer2;
    private Button btnStart2, btnPause2, btnReset2;
    private CountDownTimer countDownTimer2;
    private boolean isRunning2 = false;
    private long timeLeft2 = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timers, container, false);

        etTime1 = view.findViewById(R.id.etTime1);
        tvTimer1 = view.findViewById(R.id.tvTimer1);
        btnStart1 = view.findViewById(R.id.btnStart1);
        btnPause1 = view.findViewById(R.id.btnPause1);
        btnReset1 = view.findViewById(R.id.btnReset1);

        etTime2 = view.findViewById(R.id.etTime2);
        tvTimer2 = view.findViewById(R.id.tvTimer2);
        btnStart2 = view.findViewById(R.id.btnStart2);
        btnPause2 = view.findViewById(R.id.btnPause2);
        btnReset2 = view.findViewById(R.id.btnReset2);

        // Minutnik 1
        btnStart1.setOnClickListener(v -> startTimer1());
        btnPause1.setOnClickListener(v -> pauseTimer1());
        btnReset1.setOnClickListener(v -> resetTimer1());

        // Minutnik 2
        btnStart2.setOnClickListener(v -> startTimer2());
        btnPause2.setOnClickListener(v -> pauseTimer2());
        btnReset2.setOnClickListener(v -> resetTimer2());

        return view;
    }

    private void startTimer1() {
        if (!isRunning1) {
            if (timeLeft1 == 0) {
                long inputSec = parseLong(etTime1.getText().toString());
                if (inputSec <= 0) {
                    tvTimer1.setText("00:00.00");
                    return;
                }
                timeLeft1 = inputSec * 1000;
            }
            countDownTimer1 = new CountDownTimer(timeLeft1, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft1 = millisUntilFinished;
                    tvTimer1.setText(formatTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    isRunning1 = false;
                    tvTimer1.setText("00:00.00");
                    timeLeft1 = 0;
                }
            }.start();
            isRunning1 = true;
        }
    }

    private void pauseTimer1() {
        if (isRunning1 && countDownTimer1 != null) {
            countDownTimer1.cancel();
            isRunning1 = false;
        }
    }

    private void resetTimer1() {
        if (countDownTimer1 != null) {
            countDownTimer1.cancel();
        }
        isRunning1 = false;
        timeLeft1 = 0;
        tvTimer1.setText("00:00.00");
        etTime1.setText("");
    }

    private void startTimer2() {
        if (!isRunning2) {
            if (timeLeft2 == 0) {
                long inputSec = parseLong(etTime2.getText().toString());
                if (inputSec <= 0) {
                    tvTimer2.setText("00:00.00");
                    return;
                }
                timeLeft2 = inputSec * 1000;
            }
            countDownTimer2 = new CountDownTimer(timeLeft2, 10) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft2 = millisUntilFinished;
                    tvTimer2.setText(formatTime(millisUntilFinished));
                }

                @Override
                public void onFinish() {
                    isRunning2 = false;
                    tvTimer2.setText("00:00.00");
                    timeLeft2 = 0;
                }
            }.start();
            isRunning2 = true;
        }
    }

    private void pauseTimer2() {
        if (isRunning2 && countDownTimer2 != null) {
            countDownTimer2.cancel();
            isRunning2 = false;
        }
    }

    private void resetTimer2() {
        if (countDownTimer2 != null) {
            countDownTimer2.cancel();
        }
        isRunning2 = false;
        timeLeft2 = 0;
        tvTimer2.setText("00:00.00");
        etTime2.setText("");
    }

    private long parseLong(String val) {
        try {
            return Long.parseLong(val);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private String formatTime(long ms) {
        long totalSec = ms / 1000;
        long min = totalSec / 60;
        long sec = totalSec % 60;
        long cs = (ms % 1000) / 10;  // setne sekundy
        return String.format("%02d:%02d.%02d", min, sec, cs);
    }
}
