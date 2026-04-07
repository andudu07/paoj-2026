package com.pao.laboratory07.exercise3;

import java.util.*;
import java.util.stream.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = Integer.parseInt(sc.nextLine().trim());
        List<Comanda> comenzi = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String[] t = sc.nextLine().trim().split(" ");
            switch (t[0]) {
                case "STANDARD" -> {
                    String nume   = t[1];
                    double pret   = Double.parseDouble(t[2]);
                    String client = t[3];
                    comenzi.add(new ComandaStandard(nume, pret, client));
                }
                case "DISCOUNTED" -> {
                    String nume   = t[1];
                    double pret   = Double.parseDouble(t[2]);
                    int discount  = Integer.parseInt(t[3]);
                    String client = t[4];
                    comenzi.add(new ComandaRedusa(nume, pret, discount, client));
                }
                case "GIFT" -> {
                    String nume   = t[1];
                    String client = t[2];
                    comenzi.add(new ComandaGratuita(nume, client));
                }
            }
        }

        comenzi.forEach(c -> System.out.println(c.descriere()));

        while (sc.hasNextLine()) {
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            String[] parts = line.split(" ");
            switch (parts[0]) {
                case "STATS"   -> stats(comenzi);
                case "FILTER"  -> filter(comenzi, Double.parseDouble(parts[1]));
                case "SORT"    -> sort(comenzi);
                case "SPECIAL" -> special(comenzi);
                case "QUIT"    -> { return; }
            }
        }
    }

    private static String tipComanda(Comanda c) {
        if (c instanceof ComandaStandard)  return "STANDARD";
        if (c instanceof ComandaRedusa)    return "DISCOUNTED";
        return "GIFT";
    }

    private static void stats(List<Comanda> comenzi) {
        System.out.println("\n--- STATS ---");

        Map<String, Double> medii = comenzi.stream()
            .collect(Collectors.groupingBy(
                Main::tipComanda,
                Collectors.averagingDouble(Comanda::pretFinal)
            ));

        for (String tip : List.of("STANDARD", "DISCOUNTED", "GIFT")) {
            if (medii.containsKey(tip)) {
                System.out.printf(Locale.US, "%s: medie = %.2f lei%n", tip, medii.get(tip));
            }
        }
    }

    private static void filter(List<Comanda> comenzi, double threshold) {
        System.out.printf(Locale.US, "%n--- FILTER (>= %.2f) ---%n", threshold);

        comenzi.stream()
            .filter(c -> c.pretFinal() >= threshold)
            .forEach(c -> {
                String linie;
                if (c instanceof ComandaStandard s) {
                    linie = String.format(Locale.US, "STANDARD: %s, pret: %.2f lei - client: %s",
                        s.getNume(), s.pretFinal(), s.getClient());
                } else if (c instanceof ComandaRedusa r) {
                    linie = String.format(Locale.US, "DISCOUNTED: %s, pret: %.2f lei - client: %s",
                        r.getNume(), r.pretFinal(), r.getClient());
                } else {
                    linie = String.format("GIFT: %s, gratuit - client: %s",
                        c.getNume(), c.getClient());
                }
                System.out.println(linie);
            });
    }

    private static void sort(List<Comanda> comenzi) {
        System.out.println("\n--- SORT (by client, then by pret) ---");

        comenzi.stream()
            .sorted(Comparator.comparing(Comanda::getClient)
                .thenComparingDouble(Comanda::pretFinal))
            .forEach(c -> {
                String linie;
                if (c instanceof ComandaStandard s) {
                    linie = String.format(Locale.US, "STANDARD: %s, pret: %.2f lei - client: %s",
                        s.getNume(), s.pretFinal(), s.getClient());
                } else if (c instanceof ComandaRedusa r) {
                    linie = String.format(Locale.US, "DISCOUNTED: %s, pret: %.2f lei - client: %s",
                        r.getNume(), r.pretFinal(), r.getClient());
                } else {
                    linie = String.format("GIFT: %s, gratuit - client: %s",
                        c.getNume(), c.getClient());
                }
                System.out.println(linie);
            });
    }

    private static void special(List<Comanda> comenzi) {
        System.out.println("\n--- SPECIAL (discount > 15%) ---");

        comenzi.stream()
            .filter(c -> c instanceof ComandaRedusa r && r.getDiscountProcent() > 15)
            .forEach(c -> {
                ComandaRedusa r = (ComandaRedusa) c;
                System.out.printf(Locale.US,
                    "DISCOUNTED: %s, pret: %.2f lei (-%d%%) - client: %s%n",
                    r.getNume(), r.pretFinal(), r.getDiscountProcent(), r.getClient());
            });
    }
}
