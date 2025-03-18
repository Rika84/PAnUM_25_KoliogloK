package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.example.lab_05.data.DummyData;
import com.example.lab_05.data.Koszyk;
import com.example.lab_05.model.Przekaska;

public class PrzekaskaDetailActivity extends AppCompatActivity {

    public static final String EXTRA_PRZEKASKA_ID = "przekaskaId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_przekaska_detail);

        int przekaskaId = getIntent().getIntExtra(EXTRA_PRZEKASKA_ID, 0);
        Przekaska przekaska = DummyData.przekaski[przekaskaId];

        ImageView przekaskaImage = findViewById(R.id.przekaska_image);
        TextView przekaskaNazwa = findViewById(R.id.przekaska_nazwa);
        TextView przekaskaOpis = findViewById(R.id.przekaska_opis);
        TextView przekaskaCena = findViewById(R.id.przekaska_cena);
        Button btnDodaj = findViewById(R.id.btn_dodaj_przekaske_do_koszyka);

        przekaskaImage.setImageResource(przekaska.getImageResourceId());
        przekaskaNazwa.setText(przekaska.getNazwa());
        przekaskaOpis.setText(przekaska.getOpis());
        przekaskaCena.setText(String.format("%.2f zł", przekaska.getCena()));

        btnDodaj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Koszyk.getInstance().addPrzekaska(przekaska);
                finish();
            }
        });
    }
}
