package com.example.lab_04;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class StopwatchesFragment extends Fragment {

    // UI stoper 1
    private TextView tvTime1;
    private Button btnStart1, btnStop1, btnReset1;

    // Logika stoper 1
    private boolean isRunning1 = false;
    private long startTime1 = 0L;
    private long elapsedTime1 = 0L;
    private Handler handler1 = new Handler();

    // UI stoper 2
    private TextView tvTime2;
    private Button btnStart2, btnStop2, btnReset2;

    // Logika stoper 2
    private boolean isRunning2 = false;
    private long startTime2 = 0L;
    private long elapsedTime2 = 0L;
    private Handler handler2 = new Handler();

    // Odświeżanie co 10 ms (0,01 s)
    private Runnable updateRunnable1 = new Runnable() {
        @Override
        public void run() {
            if (isRunning1) {
                long currentTime = System.currentTimeMillis();
                long diff = currentTime - startTime1;
                tvTime1.setText(formatTime(elapsedTime1 + diff));
                handler1.postDelayed(this, 10);
            }
        }
    };

    private Runnable updateRunnable2 = new Runnable() {
        @Override
        public void run() {
            if (isRunning2) {
                long currentTime = System.currentTimeMillis();
                long diff = currentTime - startTime2;
                tvTime2.setText(formatTime(elapsedTime2 + diff));
                handler2.postDelayed(this, 10);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatches, container, false);

        // Inicjalizacja UI
        tvTime1 = view.findViewById(R.id.tvTime1);
        btnStart1 = view.findViewById(R.id.btnStart1);
        btnStop1 = view.findViewById(R.id.btnStop1);
        btnReset1 = view.findViewById(R.id.btnReset1);

        tvTime2 = view.findViewById(R.id.tvTime2);
        btnStart2 = view.findViewById(R.id.btnStart2);
        btnStop2 = view.findViewById(R.id.btnStop2);
        btnReset2 = view.findViewById(R.id.btnReset2);

        // Obsługa przycisków stoper 1
        btnStart1.setOnClickListener(v -> startStopwatch1());
        btnStop1.setOnClickListener(v -> stopStopwatch1());
        btnReset1.setOnClickListener(v -> resetStopwatch1());

        // Obsługa przycisków stoper 2
        btnStart2.setOnClickListener(v -> startStopwatch2());
        btnStop2.setOnClickListener(v -> stopStopwatch2());
        btnReset2.setOnClickListener(v -> resetStopwatch2());

        return view;
    }

    // Metody stoper 1
    private void startStopwatch1() {
        if (!isRunning1) {
            isRunning1 = true;
            startTime1 = System.currentTimeMillis();
            handler1.post(updateRunnable1);
        }
    }

    private void stopStopwatch1() {
        if (isRunning1) {
            isRunning1 = false;
            long currentTime = System.currentTimeMillis();
            elapsedTime1 += (currentTime - startTime1);
        }
    }

    private void resetStopwatch1() {
        isRunning1 = false;
        elapsedTime1 = 0L;
        tvTime1.setText(formatTime(0));
    }

    // Metody stoper 2
    private void startStopwatch2() {
        if (!isRunning2) {
            isRunning2 = true;
            startTime2 = System.currentTimeMillis();
            handler2.post(updateRunnable2);
        }
    }

    private void stopStopwatch2() {
        if (isRunning2) {
            isRunning2 = false;
            long currentTime = System.currentTimeMillis();
            elapsedTime2 += (currentTime - startTime2);
        }
    }

    private void resetStopwatch2() {
        isRunning2 = false;
        elapsedTime2 = 0L;
        tvTime2.setText(formatTime(0));
    }

    // Formatowanie do mm:ss.SS
    private String formatTime(long ms) {
        long totalSeconds = ms / 1000;
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        long centiseconds = (ms % 1000) / 10;
        return String.format("%02d:%02d.%02d", minutes, seconds, centiseconds);
    }
}
