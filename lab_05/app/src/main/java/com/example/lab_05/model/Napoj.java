package com.example.lab_05.model;

public class Napoj {
    private String nazwa;
    private String opis;
    private double cena;
    private int imageResourceId;

    public Napoj(String nazwa, String opis, double cena, int imageResourceId) {
        this.nazwa = nazwa;
        this.opis = opis;
        this.cena = cena;
        this.imageResourceId = imageResourceId;
    }

    public String getNazwa() { return nazwa; }
    public String getOpis() { return opis; }
    public double getCena() { return cena; }
    public int getImageResourceId() { return imageResourceId; }

    @Override
    public String toString() {
        return nazwa;
    }
}
