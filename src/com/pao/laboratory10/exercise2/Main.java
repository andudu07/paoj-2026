package com.pao.laboratory10.exercise2;

import com.pao.laboratory10.exercise1.Tranzactie;
import com.pao.laboratory10.exercise1.TipTranzactie;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine().trim());
        List<Tranzactie> lista = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String[] p = scanner.nextLine().trim().split("\\s+");
            int id       = Integer.parseInt(p[0]);
            double suma  = Double.parseDouble(p[1]);
            String data  = p[2];
            TipTranzactie tip = TipTranzactie.valueOf(p[3]);
            lista.add(new Tranzactie(id, suma, data, tip));
        }

        while (scanner.hasNextLine()) {
            String linie = scanner.nextLine().trim();
            if (linie.isEmpty()) continue;

            String[] parti = linie.split("\\s+");
            String comanda = parti[0];

            switch (comanda) {

                case "UNIQUE_IDS": {
                    LinkedHashSet<Integer> unice = new LinkedHashSet<>();
                    for (Tranzactie t : lista) unice.add(t.getId());
                    System.out.println("IDs unice (" + unice.size() + "): " + unice);
                    break;
                }

                case "MONTHLY_REPORT": {
                    TreeMap<String, double[]> raport = new TreeMap<>();
                    for (Tranzactie t : lista) {
                        String luna = t.getData().substring(0, 7);
                        raport.putIfAbsent(luna, new double[]{0.0, 0.0});
                        double[] sume = raport.get(luna);
                        if (t.getTip() == TipTranzactie.CREDIT) sume[0] += t.getSuma();
                        else                                      sume[1] += t.getSuma();
                    }
                    for (Map.Entry<String, double[]> entry : raport.entrySet()) {
                        double[] sume = entry.getValue();
                        System.out.printf(Locale.US, "%s: CREDIT %.2f RON, DEBIT %.2f RON%n",
                                entry.getKey(), sume[0], sume[1]);
                    }
                    break;
                }

                case "TOP": {
                    int topN = Integer.parseInt(parti[1]);
                    List<Tranzactie> copie = new ArrayList<>(lista);
                    copie.sort(Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    System.out.println("Top " + topN + ":");
                    for (int i = 0; i < topN && i < copie.size(); i++) {
                        System.out.println(copie.get(i));
                    }
                    break;
                }

                case "SORT_ASC": {
                    lista.sort(Comparator.comparingDouble(Tranzactie::getSuma));
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }

                case "SORT_DESC": {
                    lista.sort(Comparator.comparingDouble(Tranzactie::getSuma).reversed());
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }

                case "REVERSE": {
                    Collections.reverse(lista);
                    for (Tranzactie t : lista) System.out.println(t);
                    break;
                }

                case "MIN_MAX": {
                    Comparator<Tranzactie> bySuma = Comparator.comparingDouble(Tranzactie::getSuma);
                    Tranzactie min = Collections.min(lista, bySuma);
                    Tranzactie max = Collections.max(lista, bySuma);
                    System.out.println("MIN: " + min);
                    System.out.println("MAX: " + max);
                    break;
                }

                case "CME_DEMO": {
                    try {
                        for (Tranzactie t : lista) lista.remove(t);
                    } catch (ConcurrentModificationException e) {
                        System.out.println("ConcurrentModificationException prins: modificare in iteratie detectata.");
                    }
                    break;
                }

                default:
                    break;
            }
        }

        scanner.close();
    }
}