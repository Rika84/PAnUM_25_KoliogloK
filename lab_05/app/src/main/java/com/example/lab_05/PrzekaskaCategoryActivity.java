package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;

import com.example.lab_05.data.DummyData;
import com.example.lab_05.model.Przekaska;

public class PrzekaskaCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_PRZEKASKA_ID = "przekaskaId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przekaska_category);

        // adapter dla tablicy przekąsek
        ArrayAdapter<Przekaska> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                DummyData.przekaski
        );

        // Podpinanie adapter do ListView
        ListView listPrzekaski = findViewById(R.id.list_przekaski);
        listPrzekaski.setAdapter(adapter);

        // obsługa kliknięcia w konkretną przekąskę
        listPrzekaski.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // otwieranie aktywności szczegółów przekąski
                Intent intent = new Intent(PrzekaskaCategoryActivity.this, PrzekaskaDetailActivity.class);
                intent.putExtra(EXTRA_PRZEKASKA_ID, position);
                startActivity(intent);
            }
        });
    }
}
