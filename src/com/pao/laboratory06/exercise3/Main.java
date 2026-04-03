package com.pao.laboratory06.exercise3;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Inginer[] ingineri = {
                new Inginer("Popescu", "Ion", "0700000001", 8000, 50000),
                new Inginer("Andrei", "Maria", "0700000002", 12000, 30000),
                new Inginer("Zamfir", "Relu", null, 6000, 20000)
        };

        Arrays.sort(ingineri);
        System.out.println("Ingineri sortati alfabetic:");
        for (Inginer i : ingineri) System.out.println("  " + i);

        Arrays.sort(ingineri, new ComparatorInginerSalariu());
        System.out.println("Ingineri sortati dupa salariu descrescator:");
        for (Inginer i : ingineri) System.out.println("  " + i);

        PlataOnline contInginer = new Inginer("Ionescu", "Gelu", "0711111111", 9000, 15000);
        contInginer.autentificare("gelu", "parola123");
        System.out.println("Sold inginer: " + contInginer.consultareSold());
        System.out.println("Plata 500: " + contInginer.efectuarePlata(500));

        PlataOnlineSMS firma = new PersoanaJuridica("TechSRL", "SRL", "0722222222", 100000);
        firma.autentificare("techsrl", "secret");
        System.out.println("Sold firma: " + firma.consultareSold());
        System.out.println("Plata 20000: " + firma.efectuarePlata(20000));

        System.out.println("SMS trimis: " + firma.trimiteSMS("Plata de 20000 lei confirmata."));

        System.out.println("SMS mesaj gol: " + firma.trimiteSMS(""));


        System.out.println("SMS mesaj null: " + firma.trimiteSMS(null));

        PlataOnlineSMS firmaFaraTelefon = new PersoanaJuridica("MicroSRL", "SRL", null, 5000);
        System.out.println("SMS fara telefon: " + firmaFaraTelefon.trimiteSMS("Test"));

        System.out.println("SMS-uri stocate: " + ((PersoanaJuridica) firma).getSmsTrimise());


        try {
            PlataOnline contOarecare = new Inginer("Test", "User", null, 5000, 1000);
            PlataOnlineSMS smsInvalid = (PlataOnlineSMS) contOarecare;
            smsInvalid.trimiteSMS("mesaj");
        } catch (ClassCastException e) {
            System.out.println("Eroare asteptata: Inginer nu suporta SMS — " + e.getMessage());
        }

        try {
            contInginer.autentificare(null, "parola");
        } catch (IllegalArgumentException e) {
            System.out.println("Eroare asteptata: " + e.getMessage());
        }

        System.out.println("TVA: " + ConstanteFinanciare.TVA.getValoare());
        System.out.println("Salariu minim: " + ConstanteFinanciare.SALARIU_MINIM.getValoare());
        System.out.println("Cota impozit: " + ConstanteFinanciare.COTA_IMPOZIT.getValoare());
    }
}