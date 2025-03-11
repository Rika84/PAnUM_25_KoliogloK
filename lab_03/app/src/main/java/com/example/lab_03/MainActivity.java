package com.example.lab_03;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Stałe dystanse (w metrach), (z zadania na enauce)
    private final double MARATHON = 42195.0;
    private final double HALF_MARATHON = 21097.5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicjalizacja elementów interfejsu
        final EditText etPace = findViewById(R.id.etPace);             // tempo (min/km)
        final EditText etSpeed = findViewById(R.id.etSpeed);             // prędkość (km/h)
        final EditText etDistance = findViewById(R.id.etDistance);       // dodatkowy dystans (w km)
        final EditText etTargetTime = findViewById(R.id.etTargetTime);   // czas docelowy (min)
        final EditText etTargetDistance = findViewById(R.id.etTargetDistance); // dystans docelowy (km)
        Button btnCalculateFromPace = findViewById(R.id.btnCalculateFromPace);
        Button btnCalculateFromSpeed = findViewById(R.id.btnCalculateFromSpeed);
        Button btnCalculateTarget = findViewById(R.id.btnCalculateTarget);
        final TextView tvResult = findViewById(R.id.tvResult);

        // Obliczenia na podstawie podanego tempa
        btnCalculateFromPace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String paceStr = etPace.getText().toString();
                double pace;
                try {
                    pace = Double.parseDouble(paceStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Podaj prawidłowe tempo (min/km)", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (pace <= 0) {
                    Toast.makeText(MainActivity.this, "Podaj prawidłowe tempo (min/km)", Toast.LENGTH_SHORT).show();
                    return;
                }
                // a) Obliczenie prędkości (km/h)
                double speed = 60 / pace;
                // b) Czas na maraton (w sekundach)
                double timeMarathonSec = pace * (MARATHON / 1000) * 60;
                // c) Czas na półmaraton (w sekundach)
                double timeHalfMarathonSec = pace * (HALF_MARATHON / 1000) * 60;

                // d) Obliczenie czasu na dystans podany przez użytkownika (w km)
                String distanceStr = etDistance.getText().toString();
                double distanceKm = 0.0;
                if (!distanceStr.isEmpty()) {
                    try {
                        distanceKm = Double.parseDouble(distanceStr);
                    } catch (NumberFormatException e) {
                        distanceKm = 0.0;
                    }
                }
                double timeCustomSec = 0.0;
                if (distanceKm > 0) {
                    timeCustomSec = pace * distanceKm * 60;
                }

                StringBuilder result = new StringBuilder();
                result.append("Na podstawie tempa ").append(pace).append(" min/km:\n");
                result.append("Prędkość: ").append(String.format("%.2f", speed)).append(" km/h\n");
                result.append("Czas na maraton: ").append(formatTime((long) timeMarathonSec)).append("\n");
                result.append("Czas na półmaraton: ").append(formatTime((long) timeHalfMarathonSec)).append("\n");
                if (distanceKm > 0) {
                    result.append("Czas na dystans ").append(distanceKm).append(" km: ")
                            .append(formatTime((long) timeCustomSec)).append("\n");
                }
                tvResult.setText(result.toString());
            }
        });

        // Obliczenia na podstawie podanej prędkości
        btnCalculateFromSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String speedStr = etSpeed.getText().toString();
                double speed;
                try {
                    speed = Double.parseDouble(speedStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Podaj prawidłową prędkość (km/h)", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (speed <= 0) {
                    Toast.makeText(MainActivity.this, "Podaj prawidłową prędkość (km/h)", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Obliczenie tempa (min/km)
                double pace = 60 / speed;
                double timeMarathonSec = pace * (MARATHON / 1000) * 60;
                double timeHalfMarathonSec = pace * (HALF_MARATHON / 1000) * 60;

                String distanceStr = etDistance.getText().toString();
                double distanceKm = 0.0;
                if (!distanceStr.isEmpty()) {
                    try {
                        distanceKm = Double.parseDouble(distanceStr);
                    } catch (NumberFormatException e) {
                        distanceKm = 0.0;
                    }
                }
                double timeCustomSec = 0.0;
                if (distanceKm > 0) {
                    timeCustomSec = pace * distanceKm * 60;
                }

                StringBuilder result = new StringBuilder();
                result.append("Na podstawie prędkości ").append(String.format("%.2f", speed)).append(" km/h:\n");
                result.append("Tempo: ").append(String.format("%.2f", pace)).append(" min/km\n");
                result.append("Czas na maraton: ").append(formatTime((long) timeMarathonSec)).append("\n");
                result.append("Czas na półmaraton: ").append(formatTime((long) timeHalfMarathonSec)).append("\n");
                if (distanceKm > 0) {
                    result.append("Czas na dystans ").append(distanceKm).append(" km: ")
                            .append(formatTime((long) timeCustomSec)).append("\n");
                }
                tvResult.setText(result.toString());
            }
        });

        // Obliczenie wymaganego tempa i prędkości dla zadanego dystansu i czasu
        btnCalculateTarget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String targetDistanceStr = etTargetDistance.getText().toString();
                String targetTimeStr = etTargetTime.getText().toString();
                double targetDistance, targetTimeMin;
                try {
                    targetDistance = Double.parseDouble(targetDistanceStr);
                    targetTimeMin = Double.parseDouble(targetTimeStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(MainActivity.this, "Podaj prawidłowe wartości dystansu i czasu", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (targetDistance <= 0 || targetTimeMin <= 0) {
                    Toast.makeText(MainActivity.this, "Podaj prawidłowe wartości dystansu i czasu", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Obliczenie tempa (min/km) i prędkości (km/h)
                double requiredPace = targetTimeMin / targetDistance;
                double requiredSpeed = 60 / requiredPace;

                StringBuilder result = new StringBuilder();
                result.append("Aby przebiec ").append(targetDistance).append(" km w ")
                        .append(targetTimeMin).append(" minut:\n");
                result.append("Wymagane tempo: ").append(String.format("%.2f", requiredPace)).append(" min/km\n");
                result.append("Wymagana prędkość: ").append(String.format("%.2f", requiredSpeed)).append(" km/h\n");

                tvResult.setText(result.toString());
            }
        });
    }

    // Metoda formatująca czas (w sekundach) do formatu hh:mm:ss
    private String formatTime(long totalSeconds) {
        long hours = TimeUnit.SECONDS.toHours(totalSeconds);
        long minutes = TimeUnit.SECONDS.toMinutes(totalSeconds) % 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
