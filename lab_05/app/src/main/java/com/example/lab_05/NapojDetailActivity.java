package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

import com.example.lab_05.data.DummyData;
import com.example.lab_05.data.Koszyk;
import com.example.lab_05.model.Napoj;

public class NapojDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAPOJ_ID = "napojId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_napoj_detail);

        int napojId = getIntent().getIntExtra(EXTRA_NAPOJ_ID, 0);
        Napoj napoj = DummyData.napoje[napojId];

        ImageView napojImage = findViewById(R.id.napoj_image);
        TextView napojNazwa = findViewById(R.id.napoj_nazwa);
        TextView napojOpis = findViewById(R.id.napoj_opis);
        TextView napojCena = findViewById(R.id.napoj_cena);
        Button btnDodaj = findViewById(R.id.btn_dodaj_do_koszyka);

        napojImage.setImageResource(napoj.getImageResourceId());
        napojNazwa.setText(napoj.getNazwa());
        napojOpis.setText(napoj.getOpis());
        napojCena.setText(String.format("%.2f zł", napoj.getCena()));

        btnDodaj.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Koszyk.getInstance().addNapoj(napoj);
                finish(); // wracamy do listy napojów
            }
        });
    }
}
