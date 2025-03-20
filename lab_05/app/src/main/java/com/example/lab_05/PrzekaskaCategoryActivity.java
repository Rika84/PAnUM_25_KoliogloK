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

public class PrzekaskaCategoryActivity extends AppCompatActivity {

    public static final String EXTRA_PRZEKASKA_ID = "przekaskaId";
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przekaska_category);

        ListView listPrzekaski = findViewById(R.id.list_przekaski);

        // Odczyt danych z tabeli SNACK w bazie
        KafeteriaDatabaseHelper dbHelper = new KafeteriaDatabaseHelper(this);
        try {
            db = dbHelper.getReadableDatabase();
            // pobieranie _id oraz NAME z tabeli SNACK
            cursor = db.query("SNACK", new String[]{"_id", "NAME"},
                    null, null, null, null, null);

            // używamy SimpleCursorAdapter dla wyświetlienia kolumny NAME w layout simple_list_item_1
            SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0
            );
            listPrzekaski.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }

        // obsługa kliknięcia w konkretną przekąskę
        listPrzekaski.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PrzekaskaCategoryActivity.this, PrzekaskaDetailActivity.class);
                intent.putExtra(EXTRA_PRZEKASKA_ID, (int) id);
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
