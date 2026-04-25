package com.pao.laboratory08.exercise1;

import java.io.*;
import java.util.*;

public class Main {
    private static final String FILE_PATH = "src/com/pao/laboratory08/tests/studenti.txt";

    public static void main(String[] args) throws Exception {
        List<Student> studenti = citesteStudenti();

        Scanner scanner = new Scanner(System.in);
        String linie = scanner.nextLine().trim();
        String[] parti = linie.split(" ", 2);
        String comanda = parti[0];

        if (comanda.equals("PRINT")) {
            for (Student s : studenti) {
                System.out.println(s);
            }
        } else if (comanda.equals("SHALLOW")) {
            String nume = parti[1].trim();
            Student original = gaseste(studenti, nume);
            Student clona = original.shallowClone();
            clona.getAdresa().setOras("MODIFICAT");
            System.out.println("Original: " + original);
            System.out.println("Clona: " + clona);
        } else if (comanda.equals("DEEP")) {
            String nume = parti[1].trim();
            Student original = gaseste(studenti, nume);
            Student clona = original.deepClone();
            clona.getAdresa().setOras("MODIFICAT");
            System.out.println("Original: " + original);
            System.out.println("Clona: " + clona);
        }
    }

    private static List<Student> citesteStudenti() throws IOException {
        List<Student> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String linie;
            while ((linie = br.readLine()) != null) {
                if (linie.trim().isEmpty()) continue;
                String[] parts = linie.split(",");
                String nume = parts[0].trim();
                int varsta = Integer.parseInt(parts[1].trim());
                String oras = parts[2].trim();
                String strada = parts[3].trim();
                lista.add(new Student(nume, varsta, new Adresa(oras, strada)));
            }
        }
        return lista;
    }

    private static Student gaseste(List<Student> studenti, String nume) {
        return studenti.stream()
                .filter(s -> s.getNume().equals(nume))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Student negăsit: " + nume));
    }
}
