package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView;
import android.view.View;
import android.content.Intent;
import android.widget.Toast;

import com.example.lab_05.data.KafeteriaDatabaseHelper;

public class NapojCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_NAPOJ_ID = "napojId";
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napoj_category);

        ListView listNapoje = findViewById(R.id.list_napoje);

        KafeteriaDatabaseHelper dbHelper = new KafeteriaDatabaseHelper(this);
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query("DRINK", new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listNapoje.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        listNapoje.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Przekazanie _id rekordu jako EXTRA_NAPOJ_ID
                Intent intent = new Intent(NapojCategoryActivity.this, NapojDetailActivity.class);
                intent.putExtra(EXTRA_NAPOJ_ID, (int) id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) {
            cursor.close();
        }
        if (db != null) {
            db.close();
        }
    }
}
