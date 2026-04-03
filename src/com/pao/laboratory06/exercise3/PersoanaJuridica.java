package com.pao.laboratory06.exercise3;

import java.util.ArrayList;
import java.util.List;

public class PersoanaJuridica extends Persoana implements PlataOnlineSMS {
    private double sold;
    private List<String> smsTrimise = new ArrayList<>();

    public PersoanaJuridica(String nume, String prenume, String telefon, double sold) {
        super(nume, prenume, telefon);
        this.sold = sold;
    }

    @Override
    public void autentificare(String user, String parola) {
        if (user == null || user.isBlank() || parola == null || parola.isBlank())
            throw new IllegalArgumentException("User sau parola invalide.");
        System.out.println("Persoana juridica " + nume + " autentificata.");
    }

    @Override
    public double consultareSold() {
        return sold;
    }

    @Override
    public boolean efectuarePlata(double suma) {
        if (suma <= 0) return false;
        if (suma > sold) return false;
        sold -= suma;
        return true;
    }

    @Override
    public boolean trimiteSMS(String mesaj) {
        if (mesaj == null || mesaj.isBlank()) return false;
        if (telefon == null || telefon.isBlank()) return false;
        smsTrimise.add(mesaj);
        System.out.println("SMS trimis catre " + telefon + ": " + mesaj);
        return true;
    }

    public List<String> getSmsTrimise() {
        return smsTrimise;
    }

    @Override
    public String toString() {
        return "PersoanaJuridica{nume='" + nume + "', sold=" + sold + ", smsTrimise=" + smsTrimise + "}";
    }
}