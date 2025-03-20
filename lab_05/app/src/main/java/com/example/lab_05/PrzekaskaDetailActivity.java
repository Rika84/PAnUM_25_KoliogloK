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
import com.example.lab_05.model.Przekaska;

public class PrzekaskaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PRZEKASKA_ID = "przekaskaId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przekaska_detail);

        int przekaskaId = getIntent().getIntExtra(EXTRA_PRZEKASKA_ID, 0);

        ImageView przekaskaImage = findViewById(R.id.przekaska_image);
        TextView przekaskaNazwa = findViewById(R.id.przekaska_nazwa);
        TextView przekaskaOpis = findViewById(R.id.przekaska_opis);
        TextView przekaskaCena = findViewById(R.id.przekaska_cena);
        Button btnDodaj = findViewById(R.id.btn_dodaj_przekaske_do_koszyka);

        KafeteriaDatabaseHelper dbHelper = new KafeteriaDatabaseHelper(this);
        SQLiteDatabase db = null;
        Cursor cursor = null;
        Przekaska przekaska = null;

        try {
            db = dbHelper.getReadableDatabase();
            // pobieramy szczegóły przekąski z tabeli SNACK na podstawie _id
            cursor = db.query("SNACK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RES_ID", "PRICE"},
                    "_id = ?",
                    new String[]{Integer.toString(przekaskaId)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String name = cursor.getString(0);
                String description = cursor.getString(1);
                int imageResId = cursor.getInt(2);
                double price = cursor.getDouble(3);

                przekaskaNazwa.setText(name);
                przekaskaOpis.setText(description);
                przekaskaCena.setText(String.format("%.2f zł", price));
                przekaskaImage.setImageResource(imageResId);
                przekaskaImage.setContentDescription(name);

                // Tworzymy obiekt Przekaska na podstawie danych z bazy
                przekaska = new Przekaska(name, description, price, imageResId);
            } else {
                Toast.makeText(this, "Nie znaleziono przekąski", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT).show();
        } finally {
            if (cursor != null) cursor.close();
            if (db != null) db.close();
        }

        if (przekaska != null) {
            final Przekaska finalPrzekaska = przekaska;
            btnDodaj.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Koszyk.getInstance().addPrzekaska(finalPrzekaska);
                    Toast.makeText(PrzekaskaDetailActivity.this, "Dodano do koszyka", Toast.LENGTH_SHORT).show();
                    finish();
                }
            });
        }
    }
}
