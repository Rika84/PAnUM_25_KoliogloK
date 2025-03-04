package com.example.lab_01;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView arabicText;
    private TextView romanText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Применяем отступы от системных окон
        // Zastosowanie odstępów z okien systemowych
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        arabicText = findViewById(R.id.arabicText);
        romanText = findViewById(R.id.romanText);
    }

    // Обработчик нажатия для всех кнопок
    // Obsługa naciśnięć dla wszystkich przycisków
    public void onClickButton(View view) {
        String currentArabic = arabicText.getText().toString();
        int buttonId = view.getId();

        if (buttonId == R.id.button0) {
            currentArabic += "0";
        } else if (buttonId == R.id.button1) {
            currentArabic += "1";
        } else if (buttonId == R.id.button2) {
            currentArabic += "2";
        } else if (buttonId == R.id.button3) {
            currentArabic += "3";
        } else if (buttonId == R.id.button4) {
            currentArabic += "4";
        } else if (buttonId == R.id.button5) {
            currentArabic += "5";
        } else if (buttonId == R.id.button6) {
            currentArabic += "6";
        } else if (buttonId == R.id.button7) {
            currentArabic += "7";
        } else if (buttonId == R.id.button8) {
            currentArabic += "8";
        } else if (buttonId == R.id.button9) {
            currentArabic += "9";
        } else if (buttonId == R.id.button_back) {
            // Удаление последнего символа
            // Usuwanie ostatniego znaku
            if (!currentArabic.isEmpty()) {
                currentArabic = currentArabic.substring(0, currentArabic.length() - 1);
            }
        } else if (buttonId == R.id.button_clear) {
            // Очистка
            // Czyszczenie
            currentArabic = "";
        }

        // Обновляем TextView с арабскими цифрами
        // Aktualizacja TextView za pomocą cyfr arabskich
        arabicText.setText(currentArabic);

        // Конвертируем в римские, если не пусто
        // Konwersja na rzymski, jeśli nie jest puste
        if (!currentArabic.isEmpty()) {
            try {
                int number = Integer.parseInt(currentArabic);
                String roman = Convert.arabicToRoman(number);
                romanText.setText(roman);
            } catch (NumberFormatException e) {
                romanText.setText("Error");
            }
        } else {
            romanText.setText("");
        }
    }
}
