package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.view.View;
import android.widget.ImageView;

import com.example.lab_05.data.KafeteriaDatabaseHelper;

public class LocationActivity extends AppCompatActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_list);

        ListView listLocations = findViewById(R.id.list_locations);

        KafeteriaDatabaseHelper dbHelper = new KafeteriaDatabaseHelper(this);
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query("LOCATION",
                    new String[]{"_id", "NAME", "ADDRESS", "HOURS", "IMAGE_RES_ID"},
                    null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(
                        this,
                        R.layout.location_list_item,
                        cursor,
                        new String[]{"NAME", "ADDRESS", "HOURS", "IMAGE_RES_ID"},
                        new int[]{R.id.tv_location_name, R.id.tv_location_address, R.id.tv_location_hours, R.id.img_location},
                        0
                );

                adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
                    @Override
                    public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                        if (view.getId() == R.id.img_location) {
                            int resId = cursor.getInt(columnIndex);
                            ImageView imageView = (ImageView) view;
                            imageView.setImageResource(resId);
                            return true;
                        }
                        return false;
                    }
                });

                listLocations.setAdapter(adapter);
            } else {
                Toast.makeText(this, "Brak danych o lokalizacjach", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (cursor != null) cursor.close();
        if (db != null) db.close();
    }
}
