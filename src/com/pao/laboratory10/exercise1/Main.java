package com.pao.laboratory10.exercise1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        LinkedList<Tranzactie> coada = new LinkedList<>();
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String linie = scanner.nextLine().trim();
            if (linie.isEmpty()) continue;

            String[] parti = linie.split("\\s+");
            String comanda = parti[0];

            switch (comanda) {
                case "ENQUEUE": {
                    int id = Integer.parseInt(parti[1]);
                    double suma = Double.parseDouble(parti[2]);
                    String data = parti[3];
                    TipTranzactie tip = TipTranzactie.valueOf(parti[4]);
                    coada.addLast(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "DEQUEUE": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Procesat: " + t);
                    }
                    break;
                }
                case "PUSH": {
                    int id = Integer.parseInt(parti[1]);
                    double suma = Double.parseDouble(parti[2]);
                    String data = parti[3];
                    TipTranzactie tip = TipTranzactie.valueOf(parti[4]);
                    coada.addFirst(new Tranzactie(id, suma, data, tip));
                    break;
                }
                case "POP": {
                    if (coada.isEmpty()) {
                        System.out.println("Coada goala.");
                    } else {
                        Tranzactie t = coada.removeFirst();
                        System.out.println("Extras: " + t);
                    }
                    break;
                }
                case "REMOVE_DEBIT": {
                    int count = 0;
                    Iterator<Tranzactie> itr = coada.iterator();
                    while (itr.hasNext()) {
                        Tranzactie t = itr.next();
                        if (t.getTip() == TipTranzactie.DEBIT) {
                            itr.remove();
                            count++;
                        }
                    }
                    System.out.println("Eliminat " + count + " tranzactii DEBIT.");
                    break;
                }
                case "REMOVE_BELOW": {
                    double threshold = Double.parseDouble(parti[1]);
                    int count = 0;
                    Iterator<Tranzactie> itr = coada.iterator();
                    while (itr.hasNext()) {
                        Tranzactie t = itr.next();
                        if (t.getSuma() < threshold) {
                            itr.remove();
                            count++;
                        }
                    }
                    System.out.printf(Locale.US, "Eliminat %d tranzactii sub %.2f RON.%n", count, threshold);
                    break;
                }
                case "PRINT": {
                    for (Tranzactie t : coada) {
                        System.out.println(t);
                    }
                    break;
                }
                case "SIZE": {
                    System.out.println("Dimensiune coada: " + coada.size());
                    break;
                }
                default:
                    break;
            }
        }

        scanner.close();
    }
}
