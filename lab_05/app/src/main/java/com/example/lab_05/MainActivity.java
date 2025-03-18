package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.content.Intent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private String[] options = {"Napoje", "Przekąski", "Lokalizacja", "Koszyk"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listOptions = findViewById(R.id.list_options);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                options
        );
        listOptions.setAdapter(adapter);

        // Obsługa kliknięcia
        listOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // Napoje
                        startActivity(new Intent(MainActivity.this, NapojCategoryActivity.class));
                        break;
                    case 1: // Przekąski
                        startActivity(new Intent(MainActivity.this, PrzekaskaCategoryActivity.class));
                        break;
                    case 2: // Lokalizacja
                        startActivity(new Intent(MainActivity.this, LocationActivity.class));
                        break;
                    case 3: // Koszyk
                        startActivity(new Intent(MainActivity.this, KoszykActivity.class));
                        break;
                }
            }
        });
    }
}
