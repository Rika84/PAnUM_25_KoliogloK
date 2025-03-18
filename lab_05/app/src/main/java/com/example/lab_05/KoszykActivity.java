package com.example.lab_05;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.lab_05.data.Koszyk;
import com.example.lab_05.model.Napoj;
import com.example.lab_05.model.Przekaska;

public class KoszykActivity extends AppCompatActivity {

    private TextView tvKoszykLista, tvKoszykSuma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_koszyk);

        tvKoszykLista = findViewById(R.id.tv_koszyk_lista);
        tvKoszykSuma = findViewById(R.id.tv_koszyk_suma);

        Koszyk koszyk = Koszyk.getInstance();

        StringBuilder sb = new StringBuilder();
        for (Napoj n : koszyk.getNapoje()) {
            sb.append(n.getNazwa())
                    .append(" - ")
                    .append(String.format("%.2f zł", n.getCena()))
                    .append("\n");
        }
        for (Przekaska p : koszyk.getPrzekaski()) {
            sb.append(p.getNazwa())
                    .append(" - ")
                    .append(String.format("%.2f zł", p.getCena()))
                    .append("\n");
        }

        tvKoszykLista.setText(sb.toString());
        tvKoszykSuma.setText("Razem: " + String.format("%.2f zł", koszyk.getTotalPrice()));
    }
}
