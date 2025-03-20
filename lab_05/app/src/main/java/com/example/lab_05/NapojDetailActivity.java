package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;
import android.widget.Toast;

import com.example.lab_05.data.KafeteriaDatabaseHelper;
import com.example.lab_05.data.Koszyk;
import com.example.lab_05.model.Napoj;

public class NapojDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAPOJ_ID = "napojId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napoj_detail);

        int napojId = getIntent().getIntExtra(EXTRA_NAPOJ_ID, 0);

        ImageView napojImage = findViewById(R.id.napoj_image);
        TextView napojNazwa = findViewById(R.id.napoj_nazwa);
        TextView napojOpis = findViewById(R.id.napoj_opis);
        TextView napojCena = findViewById(R.id.napoj_cena);
        Button btnDodaj = findViewById(R.id.btn_dodaj_do_koszyka);

        KafeteriaDatabaseHelper dbHelper = new KafeteriaDatabaseHelper(this);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Napoj napoj = null;
        try {
            db = dbHelper.getReadableDatabase();
            cursor = db.query("DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RES_ID", "PRICE"},
                    "_id = ?",
                    new String[]{Integer.toString(napojId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);
                String description = cursor.getString(1);
                int imageResId = cursor.getInt(2);
                double price = cursor.getDouble(3);

                napojNazwa.setText(name);
                napojOpis.setText(description);
                napojCena.setText(String.format("%.2f zł", price));
                napojImage.setImageResource(imageResId);
                napojImage.setContentDescription(name);

                // Tworzenie obiektu Napoj na podstawie danych z bazy
                napoj = new Napoj(name, description, price, imageResId);
            } else {
                Toast.makeText(this, "Nie znaleziono napoju", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        // Dodanie do koszyka
        if (napoj != null) {
            Napoj finalNapoj = napoj;
            btnDodaj.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Koszyk.getInstance().addNapoj(finalNapoj);
                    Toast.makeText(NapojDetailActivity.this, "Dodano do koszyka", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}
