package com.pao.laboratory09.exercise3;

public class ATMThread extends Thread {
    private final int atmId;
    private final CoadaTranzactii coada;
    private static final int TRANZACTII_PER_ATM = 4;

    public ATMThread(int atmId, CoadaTranzactii coada) {
        this.atmId = atmId;
        this.coada = coada;
    }

    @Override
    public void run() {
        for (int i = 0; i < TRANZACTII_PER_ATM; i++) {
            Tranzactie t = new Tranzactie(atmId);
            System.out.printf(java.util.Locale.US,
                    "[ATM-%d] trimite: Tranzactie #%d %.2f RON%n",
                    atmId, t.getId(), t.getSuma());
            try {
                coada.adauga(t);
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}