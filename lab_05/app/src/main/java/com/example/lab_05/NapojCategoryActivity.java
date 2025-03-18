package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;

import com.example.lab_05.data.DummyData;
import com.example.lab_05.model.Napoj;

public class NapojCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_NAPOJ_ID = "napojId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napoj_category);

        ArrayAdapter<Napoj> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                DummyData.napoje
        );

        ListView listNapoje = findViewById(R.id.list_napoje);
        listNapoje.setAdapter(adapter);

        listNapoje.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Przejdź do szczegółów napoju
                Intent intent = new Intent(NapojCategoryActivity.this, NapojDetailActivity.class);
                intent.putExtra(EXTRA_NAPOJ_ID, position);
                startActivity(intent);
            }
        });
    }
}
