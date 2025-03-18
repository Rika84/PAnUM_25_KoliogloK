package com.example.lab_05.data;

import com.example.lab_05.model.Napoj;
import com.example.lab_05.model.Przekaska;
import java.util.ArrayList;
import java.util.List;

public class Koszyk {
    private static Koszyk instance;
    private List<Napoj> napoje;
    private List<Przekaska> przekaski;

    private Koszyk() {
        napoje = new ArrayList<>();
        przekaski = new ArrayList<>();
    }

    public static Koszyk getInstance() {
        if (instance == null) {
            instance = new Koszyk();
        }
        return instance;
    }

    public void addNapoj(Napoj n) {
        napoje.add(n);
    }

    public void addPrzekaska(Przekaska p) {
        przekaski.add(p);
    }

    public List<Napoj> getNapoje() {
        return napoje;
    }

    public List<Przekaska> getPrzekaski() {
        return przekaski;
    }

    public double getTotalPrice() {
        double sum = 0;
        for (Napoj n : napoje) {
            sum += n.getCena();
        }
        for (Przekaska p : przekaski) {
            sum += p.getCena();
        }
        return sum;
    }
}
