package com.example.lab_05.data;

import com.example.lab_05.R;
import com.example.lab_05.model.Napoj;
import com.example.lab_05.model.Przekaska;

public class DummyData {

    public static Napoj[] napoje = {
            new Napoj("Latte",
                    "Kawa latte z mleczną pianką 250ml",
                    12.50,
                    R.drawable.napoje_latte),
            new Napoj("Cappuccino",
                    "Espresso z dużą ilością spienionego mleka 250ml",
                    13.99,
                    R.drawable.napoje_cappuccino),
            new Napoj("Matcha Latte",
                    "Zielona herbata matcha z mlekiem 250ml",
                    14.00,
                    R.drawable.napoje_matcha),
            new Napoj("Mocaccino",
                    "Esencja wyśmienitej kawy ze słodyczą czekoladą 250ml",
                    11.00,
                    R.drawable.napoje_mocaccino),
            new Napoj("Herbata malinowa z rozmarynem",
                    "Przepyszna autorska herbata 300ml",
                    12.50,
                    R.drawable.napoje_herbata_malinowa_z_rozmarynem),
            new Napoj("Lemoniada malinowa",
                    "Lemoniada odświeżająca 400ml",
                    10.50,
                    R.drawable.napoje_lemoniada_malinowa),
            new Napoj("Cola origin",
                    "Coca Cola zwykła, 500ml",
                    9.50,
                    R.drawable.napoje_cola_origin),
            new Napoj("Cola zero",
                    "Coca Cola zero, 500ml",
                    9.50,
                    R.drawable.napoje_cola_zero),
            new Napoj("Woda niegazowana",
                    "Woda niegazowana Kropla Beskidu, 500ml",
                    9.50,
                    R.drawable.napoje_woda_kropla_beskidu)
    };

    public static Przekaska[] przekaski = {
            new Przekaska("Blueberry",
                    "Mus jagodowy z białą czekoladą",
                    15.00,
                    R.drawable.przekaski_blueberry),
            new Przekaska("Marakuja",
                    "Deser na bazie marakui i bitej śmietany",
                    20.50,
                    R.drawable.przekaski_marakuja),
            new Przekaska("Karmelowe love",
                    "Czekoladowo-karmelowe ciastko z orzechami",
                    22.00,
                    R.drawable.przekaski_karmelowe_love),
            new Przekaska("Cherry lady",
                    "Monodeser z wiśnią i mascarponę",
                    24.50,
                    R.drawable.przekaski_chery_lady),
            new Przekaska("Mochito",
                    "Deser z aromatyczną limonką i miętą",
                    19.50,
                    R.drawable.przekaski_mochito)
    };
}
