package com.pao.proiect.catalog;

import com.pao.proiect.catalog.exception.MaterieNedisponibilaException;
import com.pao.proiect.catalog.exception.NotaInvalidaException;
import com.pao.proiect.catalog.exception.StudentNegasitException;
import com.pao.proiect.catalog.model.*;
import com.pao.proiect.catalog.service.*;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        StudentService  ss = StudentService.getInstance();
        ProfesorService ps = ProfesorService.getInstance();
        MaterieService  ms = MaterieService.getInstance();
        CatalogService  cs = CatalogService.getInstance();

        // Actiunea 1: Adauga profesori
        System.out.println("\n--- Adauga profesori ---");
        Profesor p1 = ps.adauga(new Profesor("Ionescu", "Mihai", "m.ionescu@univ.ro", "Prof. dr.", "Informatica"));
        Profesor p2 = ps.adauga(new Profesor("Popescu", "Elena", "e.popescu@univ.ro", "Conf. dr.", "Matematica"));

        // Actiunea 2: Adauga materii
        System.out.println("\n--- Adauga materii ---");
        Materie poo     = ms.adauga(new Materie(new CodMaterie("INFO", 201), "Programare Orientata pe Obiecte", 6, p1));
        Materie analiza = ms.adauga(new Materie(new CodMaterie("MATE", 101), "Analiza Matematica", 6, p2));

        // Actiunea 1: Adauga studenti
        System.out.println("\n--- Adauga studenti ---");
        Student s1 = ss.adauga(new Student("Marin",      "Ana",   "ana@stud.ro",   "331", 3));
        Student s2 = ss.adauga(new Student("Gheorghe",   "Radu",  "radu@stud.ro",  "331", 3));
        Student s3 = ss.adauga(new Student("Constantin", "Maria", "maria@stud.ro", "332", 3));
        Student s4 = ss.adauga(new Student("Popa",       "Ion",   "ion@stud.ro",   "332", 3));

        // Actiunea 4: Inscriere la materii
        System.out.println("\n--- Inscriere studenti ---");
        try {
            cs.inscrie(s1, poo); cs.inscrie(s2, poo); cs.inscrie(s3, poo); cs.inscrie(s4, poo);
            cs.inscrie(s1, analiza); cs.inscrie(s2, analiza); cs.inscrie(s3, analiza);
        } catch (StudentNegasitException | MaterieNedisponibilaException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        // Actiunea 5: Adauga note
        System.out.println("\n--- Adauga note ---");
        try {
            cs.adaugaNota(s1, poo, 8.5);
            cs.adaugaNota(s2, poo, 6.0);
            cs.adaugaNota(s3, poo, 4.5);
            cs.adaugaNota(s4, poo, 9.0);
            cs.adaugaNota(s1, poo, 9.5);  // a doua nota pentru s1
            cs.adaugaNota(s1, analiza, 7.0);
            cs.adaugaNota(s2, analiza, 4.0);
            cs.adaugaNota(s3, analiza, 6.5);
        } catch (NotaInvalidaException | StudentNegasitException | MaterieNedisponibilaException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        // demo NotaInvalidaException
        System.out.println("\n--- Test NotaInvalidaException ---");
        try {
            cs.adaugaNota(s1, poo, 11.0);
        } catch (NotaInvalidaException e) {
            System.out.println("Exceptie prinsa: " + e.getMessage());
        } catch (StudentNegasitException | MaterieNedisponibilaException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        //Actiunea 6: calculeaza media
        System.out.println("\n--- Medii ---");
        try {
            cs.mediaLaMaterie(s1, poo).ifPresent(m ->
                    System.out.printf("Media lui %s la POO: %.2f%n", s1.getNumeComplet(), m));
            cs.mediaGenerala(s1).ifPresent(m ->
                    System.out.printf("Media generala a lui %s: %.2f%n", s1.getNumeComplet(), m));
        } catch (StudentNegasitException | MaterieNedisponibilaException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        // Actiunea 7: Listeaza studenti dintr-o grupa
        System.out.println("\n--- Studenti grupa 331 ---");
        ss.listeazaDupaGrupa("331").forEach(s -> System.out.println("  " + s));

        // Actiunea 8: Cauta student dupa nume
        System.out.println("\n--- Cautare 'mar' ---");
        ss.cautaDupaNume("mar").forEach(s -> System.out.println("  " + s));

        // demo StudentNegasitException
        System.out.println("\n--- Test StudentNegasitException ---");
        try {
            ss.cautaDupaId(9999);
        } catch (StudentNegasitException e) {
            System.out.println("Exceptie: " + e.getMessage());
        }

        // Actiunea 9: catalog complet
        System.out.println("\n--- Catalog POO ---");
        try {
            cs.afiseazaCatalog(poo);
            System.out.println("Promovati:");
            cs.studentiPromovati(poo).forEach(s -> System.out.println("  PASS " + s.getNumeComplet()));
            System.out.println("Nepromovati:");
            cs.studentiNepromovati(poo).forEach(s -> System.out.println("  FAIL " + s.getNumeComplet()));
        } catch (MaterieNedisponibilaException e) {
            System.out.println("Eroare: " + e.getMessage());
        }

        // Actiunea 10: elimina student
        System.out.println("\n--- Elimina student ---");
        System.out.println("Studenti inainte: " + ss.getNr());
        cs.eliminaStudent(s4);
        try {
            ss.elimina(s4.getId());
        } catch (StudentNegasitException e) {
            System.out.println("Eroare: " + e.getMessage());
        }
        System.out.println("Studenti dupa: " + ss.getNr());

        // materiile unui profesor
        System.out.println("\n--- Materiile lui " + p1.getNumeCuTitlu() + " ---");
        ms.materiiProfesor(p1.getId()).forEach(m -> System.out.println("  " + m));

        // studentii sortati
        System.out.println("\n--- Toti studentii (sortati) ---");
        ss.listeazaToti().forEach(s -> System.out.println("  " + s));

        System.out.println("\nDone.");
    }
}
