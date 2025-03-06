package com.example.lab_01;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private TextView arabicText;
    private TextView romanText;
    // Регулярное выражение для корректного римского числа (от 1 до 3999)
    // Wyrażenie regularne dla prawidłowej liczby rzymskiej (od 1 do 3999)
    private static final Pattern ROMAN_PATTERN = Pattern.compile(
            "^M{0,3}(CM|CD|D?C{0,3})(XC|XL|L?X{0,3})(IX|IV|V?I{0,3})$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arabicText = findViewById(R.id.arabicText);
        romanText = findViewById(R.id.romanText);
    }

    // Обработчик для арабских кнопок (синие)
    // Obsługa przycisków arabskich (niebieskich)
    public void onClickArabic(View view) {
        String current = arabicText.getText().toString();
        int id = view.getId();

        if (id == R.id.buttonClearArabic) {
            current = "";
        } else if (id == R.id.buttonBackArabic) {
            if (!current.isEmpty()) {
                current = current.substring(0, current.length() - 1);
            }
        } else {
            // Добавляем цифру
            // Dodawanie liczby
            String digit = ((TextView) view).getText().toString();
            current += digit;
        }
        arabicText.setText(current);

        // Конвертация арабского в римское
        // Konwersja arabskiego na rzymski
        if (!current.isEmpty()) {
            try {
                int number = Integer.parseInt(current);
                String roman = Convert.arabicToRoman(number);
                romanText.setText(roman);
            } catch (NumberFormatException e) {
                romanText.setText("Error");
            }
        } else {
            romanText.setText("");
        }
    }

    // Обработчик для римских кнопок (зелёные)
    // Obsługa przycisków rzymskich (zielony)
    public void onClickRoman(View view) {
        String current = romanText.getText().toString();
        int id = view.getId();

        // Стираем последний римский символ
        // Usuwanie ostatniego symbolu rzymskiego
        if (id == R.id.buttonBackRoman) {
            if (!current.isEmpty()) {
                current = current.substring(0, current.length() - 1);
            }
        } else {
            // Добавляем символ (например, M, D, C, L, X, V, I)
            // Dodanie znaku (np. M, D, C, L, X, V, I)
            String symbol = ((TextView) view).getText().toString();
            String newRoman = current + symbol;
            // Проверяем корректность римской записи
            // Sprawdzenie, czy zapis rzymski jest poprawny
            if (!newRoman.isEmpty() && !ROMAN_PATTERN.matcher(newRoman).matches()) {
                Toast.makeText(this, "Błąd w rzymskim zapisie!", Toast.LENGTH_SHORT).show();
                return;
            }
            current = newRoman;
        }

        romanText.setText(current);

        // Конвертация римского в арабское
        // Konwersja rzymskiego na arabski
        if (!current.isEmpty()) {
            String result = Convert.romanToArabic(current);
            arabicText.setText(result);
        } else {
            arabicText.setText("");
        }
    }

}
