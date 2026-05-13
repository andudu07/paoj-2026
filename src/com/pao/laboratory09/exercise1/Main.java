package com.pao.laboratory09.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String OUTPUT_FILE = "output/lab09_ex1.ser";

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine().trim());
        List<Tranzactie> tranzactii = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine().trim();
            String[] parts = line.split("\\s+");
            int id             = Integer.parseInt(parts[0]);
            double suma        = Double.parseDouble(parts[1]);
            String data        = parts[2];
            String contSursa   = parts[3];
            String contDest    = parts[4];
            TipTranzactie tip  = TipTranzactie.valueOf(parts[5]);

            Tranzactie t = new Tranzactie(id, suma, data, contSursa, contDest, tip);

            t.setNote("procesat");

            tranzactii.add(t);
        }

        new File("output").mkdirs();

        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(OUTPUT_FILE))) {
            oos.writeObject(tranzactii);
        }

        List<Tranzactie> deserializate;
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(OUTPUT_FILE))) {
            @SuppressWarnings("unchecked")
            List<Tranzactie> temp = (List<Tranzactie>) ois.readObject();
            deserializate = temp;
        }

        while (scanner.hasNextLine()) {
            String command = scanner.nextLine().trim();
            if (command.isEmpty()) continue;

            if (command.equals("LIST")) {
                for (Tranzactie t : deserializate) {
                    System.out.println(t);
                }

            } else if (command.startsWith("FILTER ")) {
                String prefix = command.substring(7).trim();
                boolean found = false;
                for (Tranzactie t : deserializate) {
                    if (t.getData().startsWith(prefix)) {
                        System.out.println(t);
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Niciun rezultat.");
                }

            } else if (command.startsWith("NOTE ")) {
                int targetId = Integer.parseInt(command.substring(5).trim());
                Tranzactie found = null;
                for (Tranzactie t : deserializate) {
                    if (t.getId() == targetId) {
                        found = t;
                        break;
                    }
                }
                if (found == null) {
                    System.out.println("NOTE[" + targetId + "]: not found");
                } else {
                    System.out.println("NOTE[" + targetId + "]: " + found.getNote());
                }
            }
        }

        scanner.close();
    }
}