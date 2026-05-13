package com.pao.laboratory09.exercise3;

public class Tranzactie {
    private static int contor = 1;

    private final int id;
    private final int atmId;
    private final double suma;
    private final String data;

    public Tranzactie(int atmId) {
        synchronized (Tranzactie.class) {
            this.id = contor++;
        }
        this.atmId = atmId;
        this.suma = Math.round((100 + Math.random() * 4900) * 100.0) / 100.0;
        this.data = "2024-01-" + String.format("%02d", (int)(Math.random() * 28) + 1);
    }

    public int getId()    { return id; }
    public int getAtmId() { return atmId; }
    public double getSuma() { return suma; }
    public String getData() { return data; }
}