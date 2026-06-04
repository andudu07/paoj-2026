package com.pao.proiect.catalog;

import com.pao.proiect.catalog.model.db.*;
import com.pao.proiect.catalog.repository.*;
import com.pao.proiect.catalog.service.AuditService;
import com.pao.proiect.catalog.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

/**
 *  - CRUD complet pentru 4 entitati
 *  - ≥1 tranzactie JDBC explicita cu commit/rollback
 *  - ≥3 interogari SQL cu JOIN
 *  - AuditService apelat din 10 actiuni distincte
 */
public class Exercise2Main {

    public static void main(String[] args) {
        AuditService    audit   = AuditService.getInstance();
        ProfesorRepository proRepo = new ProfesorRepository();
        StudentRepository  stuRepo = new StudentRepository();
        MaterieRepository  matRepo = new MaterieRepository();
        NotaRepository     notRepo = new NotaRepository();

        try {
            // ACTIUNEA 1 - Adauga profesori
            System.out.println("\n=== ACTIUNEA 1: Adauga profesori ===");
            ProfesorDb p1 = new ProfesorDb("Ionescu", "Mihai", "m.ionescu@univ.ro",
                                           "Prof. dr.", "Informatica");
            ProfesorDb p2 = new ProfesorDb("Popescu", "Elena", "e.popescu@univ.ro",
                                           "Conf. dr.", "Matematica");
            proRepo.save(p1);
            proRepo.save(p2);
            audit.log("ADAUGA_PROFESOR");                          // actiune 1
            System.out.println("Salvat: " + p1);
            System.out.println("Salvat: " + p2);

            // ACTIUNEA 2 - Adauga studenti
            System.out.println("\n=== ACTIUNEA 2: Adauga studenti ===");
            StudentDb s1 = new StudentDb("Marin",      "Ana",   "ana@stud.ro",   "331", 3);
            StudentDb s2 = new StudentDb("Gheorghe",   "Radu",  "radu@stud.ro",  "331", 3);
            StudentDb s3 = new StudentDb("Constantin", "Maria", "maria@stud.ro", "332", 3);
            StudentDb s4 = new StudentDb("Popa",       "Ion",   "ion@stud.ro",   "332", 3);
            stuRepo.save(s1); stuRepo.save(s2);
            stuRepo.save(s3); stuRepo.save(s4);
            audit.log("ADAUGA_STUDENT");                           // actiune 2
            System.out.println("Studenti salvati cu id-urile: "
                    + s1.getId() + ", " + s2.getId() + ", "
                    + s3.getId() + ", " + s4.getId());


            // ACTIUNEA 3 - Adauga materii (cu JOIN: materie -> profesor)

            System.out.println("\n=== ACTIUNEA 3: Adauga materii ===");
            MaterieDb m1 = new MaterieDb("INFO201", "Programare Orientata pe Obiecte",
                                         6, p1.getId());
            MaterieDb m2 = new MaterieDb("MATE101", "Analiza Matematica", 6, p2.getId());
            matRepo.save(m1);
            matRepo.save(m2);
            audit.log("ADAUGA_MATERIE");                           // actiune 3
            System.out.println("Materii salvate: " + m1 + ", " + m2);

            // ACTIUNEA 4 - Listeaza toate materiile
            System.out.println("\n=== ACTIUNEA 4: Listeaza materii ===");
            List<MaterieDb> toateMateriile = matRepo.findAll();
            toateMateriile.forEach(m -> System.out.println("  " + m));
            audit.log("LISTEAZA_MATERII");                         // actiune 4

            // ACTIUNEA 5 — Tranzactie: adauga note pentru s1 la m1
            //              Daca una esueaza, toate fac rollback
            System.out.println("\n=== ACTIUNEA 5: Tranzactie — adauga note ===");
            Connection conn = DatabaseConnection.getInstance().getConnection();
            conn.setAutoCommit(false);                // incepem tranzactia
            try {
                String azi = LocalDate.now().toString();

                NotaDb n1 = new NotaDb(s1.getId(), m1.getId(), 8.50, azi);
                NotaDb n2 = new NotaDb(s2.getId(), m1.getId(), 6.00, azi);
                NotaDb n3 = new NotaDb(s3.getId(), m1.getId(), 4.50, azi);
                NotaDb n4 = new NotaDb(s4.getId(), m1.getId(), 9.00, azi);
                NotaDb n5 = new NotaDb(s1.getId(), m2.getId(), 7.00, azi);
                NotaDb n6 = new NotaDb(s2.getId(), m2.getId(), 4.00, azi);
                NotaDb n7 = new NotaDb(s3.getId(), m2.getId(), 6.50, azi);

                notRepo.save(n1); notRepo.save(n2); notRepo.save(n3);
                notRepo.save(n4); notRepo.save(n5); notRepo.save(n6);
                notRepo.save(n7);

                conn.commit();                        // succes - commit
                System.out.println("Tranzactie COMMIT — 7 note salvate.");
                audit.log("ADAUGA_NOTE_TRANZACTIE");             // actiune 5

            } catch (SQLException ex) {
                conn.rollback();                      // esec - rollback
                System.err.println("Tranzactie ROLLBACK: " + ex.getMessage());
                audit.log("ROLLBACK_NOTE");
                throw ex;
            } finally {
                conn.setAutoCommit(true);             // restauram modul normal
            }


            // ACTIUNEA 6 - JOIN #1: materiile unui profesor

            System.out.println("\n=== ACTIUNEA 6: JOIN — Materiile prof. Ionescu ===");
            List<MaterieDb> materiiP1 = matRepo.findByProfesor(p1.getId());
            materiiP1.forEach(m ->
                System.out.printf("  Materie: %s — %s (%d credite)%n",
                        m.getCod(), m.getDenumire(), m.getCredite()));
            audit.log("LISTEAZA_MATERII_PROFESOR");               // actiune 6

            // ACTIUNEA 7 - JOIN #2: notele unui student cu detalii

            System.out.println("\n=== ACTIUNEA 7: JOIN — Notele lui " + s1.getPrenume() + " " + s1.getNume() + " ===");
            List<String[]> noteAna = notRepo.findNoteCuDetalii(s1.getId());
            noteAna.forEach(row ->
                System.out.printf("  Student: %-15s | Materie: %-35s | Profesor: %-20s | Nota: %s | Data: %s%n",
                        row[0], row[1], row[2], row[3], row[4]));
            audit.log("VIZUALIZEAZA_NOTE_STUDENT");               // actiune 7

            // ACTIUNEA 8 - JOIN #3: media studentilor la o materie

            System.out.println("\n=== ACTIUNEA 8: JOIN — Medii la " + m1.getDenumire() + " ===");
            List<String[]> medii = notRepo.mediePeStudentiLaMaterie(m1.getId());
            medii.forEach(row ->
                System.out.printf("  %-20s (Grupa %s) — Media: %s%n",
                        row[0], row[1], row[2]));
            audit.log("CALCULEAZA_MEDII_MATERIE");                // actiune 8

            // ACTIUNEA 9 - Update si findById

            System.out.println("\n=== ACTIUNEA 9: Update student ===");
            s1.setGrupa("333");
            s1.setEmail("ana.marin@stud.ro");
            stuRepo.update(s1);
            StudentDb s1Updated = stuRepo.findById(s1.getId()).orElseThrow();
            System.out.println("Dupa update: " + s1Updated);
            audit.log("UPDATE_STUDENT");                          // actiune 9

            // ACTIUNEA 10 - Stergere student (cu curatare note prin FK)

            System.out.println("\n=== ACTIUNEA 10: Sterge student ===");
            System.out.println("Studenti inainte: " + stuRepo.findAll().size());

            // Intai stergem notele, apoi studentul
            notRepo.deleteByStudent(s4.getId());
            stuRepo.delete(s4.getId());

            System.out.println("Studenti dupa: " + stuRepo.findAll().size());
            audit.log("STERGE_STUDENT");                          // actiune 10

            // Sumar final

            System.out.println("\n=== SUMAR FINAL ===");
            System.out.println("Profesori in DB : " + proRepo.findAll().size());
            System.out.println("Studenti in DB  : " + stuRepo.findAll().size());
            System.out.println("Materii in DB   : " + matRepo.findAll().size());
            System.out.println("Note in DB      : " + notRepo.findAll().size());
            System.out.println("\nAudit scris in: audit.csv (10 actiuni)");
            System.out.println("Done.");

        } catch (Exception e) {
            System.err.println("Eroare: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try { DatabaseConnection.getInstance().close(); }
            catch (Exception ignored) {}
        }
    }
}
