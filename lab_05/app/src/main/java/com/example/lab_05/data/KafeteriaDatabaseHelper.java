package com.example.lab_05.data;

import com.example.lab_05.R;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class KafeteriaDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "kafeteria.db";
    private static final int DB_VERSION = 3;

    public KafeteriaDatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tworzenie tabeli DRINK
        db.execSQL("CREATE TABLE DRINK (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "DESCRIPTION TEXT, " +
                "IMAGE_RES_ID INTEGER, " +
                "PRICE REAL);");

        // Tworzenie tabeli SNACK
        db.execSQL("CREATE TABLE SNACK (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "DESCRIPTION TEXT, " +
                "IMAGE_RES_ID INTEGER, " +
                "PRICE REAL);");

        // Tworzenie tabeli LOCATION
        db.execSQL("CREATE TABLE LOCATION (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "NAME TEXT, " +
                "ADDRESS TEXT, " +
                "HOURS TEXT, " +
                "IMAGE_RES_ID INTEGER);");

        // Wstawianie danych startowych do tabeli DRINK
        insertDrink(db, "Latte", "Kawa latte z mleczną pianką 250ml", R.drawable.napoje_latte, 12.50);
        insertDrink(db, "Cappuccino", "Espresso z dużą ilością spienionego mleka 250ml", R.drawable.napoje_cappuccino, 13.99);
        insertDrink(db, "Matcha Latte", "Zielona herbata matcha z mlekiem 250ml", R.drawable.napoje_matcha, 14.00);
        insertDrink(db, "Mocaccino", "Esencja wyśmienitej kawy ze słodyczą czekoladą 250ml", R.drawable.napoje_mocaccino, 11.00);
        insertDrink(db, "Herbata malinowa z rozmarynem", "Przepyszna autorska herbata 300ml", R.drawable.napoje_herbata_malinowa_z_rozmarynem, 12.50);
        insertDrink(db, "Lemoniada malinowa", "Lemoniada odświeżająca 400ml", R.drawable.napoje_lemoniada_malinowa, 10.50);
        insertDrink(db, "Cola origin", "Coca Cola zwykła, 500ml", R.drawable.napoje_cola_origin, 9.50);
        insertDrink(db, "Cola zero", "Coca Cola zero, 500ml", R.drawable.napoje_cola_zero, 9.50);
        insertDrink(db, "Woda niegazowana", "Woda niegazowana Kropla Beskidu, 500ml", R.drawable.napoje_woda_kropla_beskidu, 9.50);

        // Wstawianie danych startowych do tabeli SNACK
        insertSnack(db, "Blueberry", "Mus jagodowy z białą czekoladą", R.drawable.przekaski_blueberry, 15.00);
        insertSnack(db, "Marakuja", "Deser na bazie marakui i bitej śmietany", R.drawable.przekaski_marakuja, 20.50);
        insertSnack(db, "Karmelowe love", "Czekoladowo-karmelowe ciastko z orzechami", R.drawable.przekaski_karmelowe_love, 22.00);
        insertSnack(db, "Cherry lady", "Monodeser z wiśnią i mascarpone", R.drawable.przekaski_chery_lady, 24.50);
        insertSnack(db, "Mochito", "Deser z aromatyczną limonką i miętą", R.drawable.przekaski_mochito, 19.50);
        insertSnack(db, "Pistacja", "Ciastko pistacjowo-czekoladowe", R.drawable.przekaski_pistacja, 26.50);

        // Wstawianie danych startowych do tabeli LOCATION
        insertLocation(db, "FC Caffe", "ul. Kuźnica 20, 50-138 Wrocław", "8:00 - 21:00", R.drawable.mapa);
        insertLocation(db, "Patisserie On Time", "al. NMP 49, 42-218 Częstochowa", "7:00 - 20:00", R.drawable.map2);
    }

    private void insertDrink(SQLiteDatabase db, String name, String description, int imageResId, double price) {
        db.execSQL("INSERT INTO DRINK (NAME, DESCRIPTION, IMAGE_RES_ID, PRICE) VALUES ('" +
                name + "', '" +
                description + "', " +
                imageResId + ", " +
                price + ");");
    }

    private void insertSnack(SQLiteDatabase db, String name, String description, int imageResId, double price) {
        db.execSQL("INSERT INTO SNACK (NAME, DESCRIPTION, IMAGE_RES_ID, PRICE) VALUES ('" +
                name + "', '" +
                description + "', " +
                imageResId + ", " +
                price + ");");
    }

    private void insertLocation(SQLiteDatabase db, String name, String address, String hours, int imageResId) {
        db.execSQL("INSERT INTO LOCATION (NAME, ADDRESS, HOURS, IMAGE_RES_ID) VALUES ('" +
                name + "', '" +
                address + "', '" +
                hours + "', " +
                imageResId + ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS DRINK");
        db.execSQL("DROP TABLE IF EXISTS SNACK");
        db.execSQL("DROP TABLE IF EXISTS LOCATION");
        onCreate(db);
    }
}
